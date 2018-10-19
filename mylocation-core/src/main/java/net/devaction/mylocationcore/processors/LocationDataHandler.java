package net.devaction.mylocationcore.processors;

import org.apache.logging.log4j.Logger;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocationcore.util.LocationDataUtil;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataHandler implements Handler<Future<LocationDataProcessingResult>>{
    private static final Logger log = LogManager.getLogger(LocationDataHandler.class);
    
    private final JsonObject locationDataJson;
    private final LocationDataProcessor locationDataProcessor;
    
    public LocationDataHandler(JsonObject locationDataJson, LocationDataProcessor locationDataProcessor){
        this.locationDataJson = locationDataJson;
        this.locationDataProcessor = locationDataProcessor;
    }

    @Override
    public void handle(Future<LocationDataProcessingResult> future) {
        LocationData locationData = LocationDataUtil.constructObject(locationDataJson);
        LocationDataProcessingResult processingResult = locationDataProcessor.process(locationData);
        
        future.complete(processingResult);
    }
}

