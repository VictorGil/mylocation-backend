package net.devaction.mylocationcore.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

/**
 * @author Víctor Gil
 * since October 2018 
 * 
 */
//We consciously avoid a method to retrieve a float configuration value 
public class ConfValueProviderImpl implements ConfValueProvider{
    private static final Logger log = LoggerFactory.getLogger(ConfValueProviderImpl.class);

    private static JsonObject appConfig;

    @Override
    public String getString(String key) {
        return appConfig.getString(key);
    }

    @Override
    public Integer getInteger(String key) {
        return appConfig.getInteger(key);
    }

    @Override
    public Double getDouble(String key) {
        return appConfig.getDouble(key);
    }

    @Override
    public Long getLong(String key) {
        return appConfig.getLong(key);
    }

    public static void setAppConfig(JsonObject appConfigParam){
        appConfig = appConfigParam;
    }        
}

