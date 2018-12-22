package net.devaction.httptest;

import net.devaction.mylocationcore.test.config.AppConfigProvider;
import net.devaction.mylocationcore.util.DecryptedValueProvider;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class HttpSender{
    private static final Logger log = LoggerFactory.getLogger(HttpSender.class);

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
     
    public static void send(String url, String jsonString){
        
        final String keyStorePasswordEncrypted = AppConfigProvider.provide().getKeystorePasswordEncrypted();
        final String keyStorePassword = decrypt(keyStorePasswordEncrypted);
        
        final String keyStoreFile = AppConfigProvider.provide().getWebServerKeystoreFile();
        final SSLSocketFactory sslSocketFactory = new SslSocketFactoryCreator().create(keyStoreFile, keyStorePassword);
        
        final OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory).
                hostnameVerifier(
                        new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session){
                                //I do not verify the hostname
                                //because I am going to deploy to AWS so I am not sure about the hostname
                                //where the Vert.x code will run
                                return true;
                            }
                        }
                ).build();

        final Request request = buildRequest(url, jsonString);
        Response response = null;
        try{
            response = client.newCall(request).execute();
            log.info("HTTP Response received: " + response.body().string());
        } catch(IOException ex){
            log.error(ex.toString(), ex);
        }
        log.info("Response: " + response);
    }

    static Request buildRequest(String url, String jsonString){
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonString);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return request;
    }

    private static String decrypt(String encryptedValue){
        final String decryptPasswordEnvVarName = AppConfigProvider.provide().getDecryptPasswordEnvVarName();
        
        final String encryptionPassword = System.getenv(decryptPasswordEnvVarName);
        if (encryptionPassword == null || encryptionPassword.length() == 0) {
            String errMsg = "FATAL: The decryption/encryption password cannot be null nor empty";
            log.error(errMsg);
            throw new RuntimeException(errMsg);
        }  
        
        return decrypt(encryptedValue, encryptionPassword);        
    }
    
    private static String decrypt(String encryptedValue, String encryptionPassword){
        log.debug("Encryption/decryption password: " + encryptionPassword);
        DecryptedValueProvider decryptedValueProvider = new DecryptedValueProvider();
        decryptedValueProvider.setEncryptionPassword(encryptionPassword);
        try{
            decryptedValueProvider.afterPropertiesSet();
        } catch(Exception ex){
            log.error(ex.toString(), ex);
            throw new RuntimeException(ex);
        }
        
        return decryptedValueProvider.decrypt(encryptedValue);        
    }
}

