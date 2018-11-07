package net.devaction.mylocationcore.test.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Víctor Gil
 * 
 * since November 2018
 */
public class AppConfigProviderTester {
    private static final Logger log = LogManager.getLogger(AppConfigProviderTester.class);
    
    public static void main(String[] arguments){
        new AppConfigProviderTester().run();
    }
    
    private void run(){
        log.info("Starting");
        AppConfig appConfig = AppConfigProvider.provide();
        
        log.info("AppConfig from JSON file: " + appConfig);
    }
}

