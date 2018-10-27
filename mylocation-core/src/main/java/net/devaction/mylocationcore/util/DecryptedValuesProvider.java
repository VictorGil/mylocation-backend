package net.devaction.mylocationcore.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;

/**
 * @author Víctor Gil
 */
public class DecryptedValuesProvider{
    private static final Logger log = LogManager.getLogger(DecryptedValuesProvider.class);

    private final StandardPBEStringEncryptor encryptor;
    
    public DecryptedValuesProvider(String encryptionPassword){
        SimplePBEConfig config = new SimplePBEConfig(); 
        config.setPassword(encryptionPassword);
    
        encryptor = new StandardPBEStringEncryptor(); 
        encryptor.setConfig(config);
        encryptor.initialize();
    }
    
    public String  decrypt(String encryptedValue){
        return encryptor.decrypt(encryptedValue);
    }
}
