package net.devaction.mylocationcore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
import org.springframework.beans.factory.InitializingBean;

import net.devaction.mylocationcore.di.ConfValueProvider;

/**
 * @author Víctor Gil
 * since October 2018
 */
public class DecryptedValueProvider implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(DecryptedValueProvider.class);

    private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    
    private String encryptionPassword;
    private ConfValueProvider confValueProvider;   
        
    public String decrypt(String encryptedValue){
        return encryptor.decrypt(encryptedValue);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (encryptionPassword == null) {
            String decryptPasswordEnvVarName = confValueProvider.getString("decrypt_password_env_var_name");
            log.debug("Environment variable to be used to obtain the decryption/encryption password: " + 
                    decryptPasswordEnvVarName);
            
            encryptionPassword = System.getenv(decryptPasswordEnvVarName);
            if (encryptionPassword == null || encryptionPassword.length() == 0) {
                String errMsg = "FATAL: The decryption/encryption password cannot be null nor empty";
                log.error(errMsg);
                throw new RuntimeException(errMsg);
            }            
        }
        
        SimplePBEConfig config = new SimplePBEConfig(); 
        config.setPassword(encryptionPassword);
        
        encryptor.setConfig(config);
        encryptor.initialize();
    }
    
    //this may be useful when testing
    public void setEncryptionPassword(String encryptionPassword) {
        this.encryptionPassword = encryptionPassword;
    }

    //to be called by Spring
    public void setConfValueProvider(ConfValueProvider confValueProvider) {
        this.confValueProvider = confValueProvider;
    }
}

