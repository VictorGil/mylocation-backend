package net.devaction.mylocationcore.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocationcore.util.LocationDataUtil;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataHandler implements Handler<Future<LocationDataProcessingResult>>{
    private static final Logger log = LoggerFactory.getLogger(LocationDataHandler.class);
    
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

