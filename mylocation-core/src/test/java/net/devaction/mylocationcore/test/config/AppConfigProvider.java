package net.devaction.mylocationcore.test.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Víctor Gil
 * since November 2018
 */
public class AppConfigProvider {
    private static final Logger log = LogManager.getLogger(AppConfigProvider.class);
    
    private static AppConfig appConfig;
    
    private AppConfigProvider(){}
    
    public static AppConfig provide(){        
        if (appConfig == null){
            appConfig = new AppConfigProvider().provideNonStatic();
        }
                  
        return appConfig;
    }
    
    private AppConfig provideNonStatic(){
        InputStream jsonInputStream = this.getClass().getResourceAsStream("/config.json");
        AppConfigWrapper appConfigWrapper;
        try{
            appConfigWrapper = new ObjectMapper().readValue(jsonInputStream, AppConfigWrapper.class);
        } catch(IOException ex){
            log.fatal(ex, ex);
            throw new RuntimeException(ex);
        } finally{
            try{
                jsonInputStream.close();
            } catch(IOException ex){
                log.error("Exception when closing the input stream: " + ex, ex);               
            }
        }
        
        return appConfigWrapper.getAppConfig();
    }    
}

