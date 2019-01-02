package net.devaction.mylocationcore.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Víctor Gil
 * since November 2018
 */
public class AppConfigWrapper {
    private static final Logger log = LoggerFactory.getLogger(AppConfigWrapper.class);
    
    @JsonProperty("application_configuration")
    private AppConfig appConfig;

    @Override
    public String toString() {
        return "AppConfigWrapper [application_configuration=" + appConfig + "]";
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }    
}

