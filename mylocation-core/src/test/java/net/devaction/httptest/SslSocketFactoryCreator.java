package net.devaction.httptest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author Víctor Gil
 * 
 * since November 2018
 * 
 * This class is similar to one in the mylocation-android with identical name
 * 
 */
public class SslSocketFactoryCreator{
    private static final Logger log = LoggerFactory.getLogger(SslSocketFactoryCreator.class);
    
    public SSLSocketFactory create(String keyStoreFile, String keyStorePassword){
        
        log.debug("Keystore file: " + keyStoreFile);
        SSLSocketFactory sSLSocketFactory = null;
        InputStream keyStoreInputStream = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            
            //Please note, in Android we use PKCS12 as format of the key store:
            //KeyStore keyStore = KeyStore.getInstance("PKCS12");
            
            keyStoreInputStream = this.getClass().getResourceAsStream("/" + keyStoreFile);
            keyStore.load(keyStoreInputStream, keyStorePassword.toCharArray());
            log.debug("Loaded key store file");

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

            String trustManagerFactoryDefaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            log.debug("TrustManagerFactory default algorithm: " +
                    trustManagerFactoryDefaultAlgorithm); //it is PKIX 

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryDefaultAlgorithm);
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            sSLSocketFactory = sslContext.getSocketFactory();
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            throw new RuntimeException(ex);
        } finally {
            if (keyStoreInputStream != null)
                try{
                    log.debug("Closing the input stream");
                    keyStoreInputStream.close();
                } catch(IOException ex){
                    log.error("Exception when closing the input stream: " + ex, ex);
                }
        }
        
        return sSLSocketFactory;
    }
}

