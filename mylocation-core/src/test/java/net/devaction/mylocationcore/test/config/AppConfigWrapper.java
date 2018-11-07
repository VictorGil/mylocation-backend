package net.devaction.mylocationcore.test.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Víctor Gil
 * since November 2018
 */
public class AppConfigWrapper {
    private static final Logger log = LogManager.getLogger(AppConfigWrapper.class);
    
    @JsonProperty("app_config")
    private AppConfig appConfig;

    @Override
    public String toString() {
        return "AppConfigWrapper [appConfig=" + appConfig + "]";
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }    
}

