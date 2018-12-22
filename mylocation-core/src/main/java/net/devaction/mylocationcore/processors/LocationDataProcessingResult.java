package net.devaction.mylocationcore.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocationcore.sharedenums.Result;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataProcessingResult{
    private static final Logger log = LoggerFactory.getLogger(LocationDataProcessingResult.class);
    
    private final Result result;    
    private final LocationData locationData;

    public LocationDataProcessingResult(Result result, LocationData locationData){
        this.result = result;
        this.locationData = locationData;
    }
    
    public Result getResult() {
        return result;
    }

    public LocationData getLocationData() {
        return locationData;
    }

    @Override
    public String toString() {
        return "LocationDataProcessorResult [result=" + result + ", locationData=" + locationData + "]";
    }    
}

