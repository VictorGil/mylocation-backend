package net.devaction.mylocationcore.util;

import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.api.data.LocationData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataUtil{

private static final Logger log = LoggerFactory.getLogger(LocationDataUtil.class);

    public static LocationData constructObject(String locationDataJsonString){
        log.trace("Going to create a LocationData object from this JSON String: " + locationDataJsonString);
        JsonObject locationDataJsonObject = new JsonObject(locationDataJsonString);
        return constructObject(locationDataJsonObject);
    }

    public static LocationData constructObject(JsonObject locationDataJsonObject){
        log.trace("LocationData JsonObject: " + locationDataJsonObject);
        LocationData locationData = locationDataJsonObject.mapTo(LocationData.class);
        log.trace("New LocationData object created from JSON: " + locationData);
        return locationData;
    }
    
    public static JsonObject convertToJsonObject(LocationData locationData){
        log.trace("Going to serialize LocationData object to JSON:" + locationData);
        JsonObject locationDataJsonObject = JsonObject.mapFrom(locationData);
        log.trace("Auxiliar locationData JsonObject: " + locationDataJsonObject);
        
        return locationDataJsonObject;
    }
    
    public static String convertToJsonString(LocationData locationData){
        return convertToJsonObject(locationData).toString();
    }
}

