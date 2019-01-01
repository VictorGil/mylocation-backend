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
    
    @JsonProperty("core_service_config")
    private AppConfig appConfig;

    @Override
    public String toString() {
        return "AppConfigWrapper [core_service_config=" + appConfig + "]";
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }    
}

