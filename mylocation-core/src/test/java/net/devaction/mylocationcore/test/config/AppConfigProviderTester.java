package net.devaction.mylocationcore.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since November 2018
 */
public class AppConfigProviderTester {
    private static final Logger log = LoggerFactory.getLogger(AppConfigProviderTester.class);
    
    public static void main(String[] arguments){
        new AppConfigProviderTester().run();
    }
    
    private void run(){
        log.info("Starting");
        AppConfig appConfig = AppConfigProvider.provide();
        
        log.info("AppConfig from JSON file: " + appConfig);
    }
}

