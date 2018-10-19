package net.devaction.mylocationcore.processors;

import org.apache.logging.log4j.Logger;

import io.vertx.core.eventbus.EventBus;
import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocationcore.sharedenums.Result;
import net.devaction.mylocationcore.util.LocationDataUtil;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataProcessor{
    private static final Logger log = LogManager.getLogger(LocationDataProcessor.class);
    
    private final EventBus eventBus;
    private final String eventBusMulticastAddress; 
    
    public LocationDataProcessor(EventBus eventBus, String eventBusMulticastAddress){
        this.eventBus = eventBus;
        this.eventBusMulticastAddress = eventBusMulticastAddress;
    }
    
    public LocationDataProcessingResult process(LocationData locationData){       
        log.debug("Going to process LocationData: " + locationData);
        
        eventBus.publish(eventBusMulticastAddress, LocationDataUtil.convertToJsonString(locationData));       
        
        log.debug("Processing of LocationData finished: " + locationData);
     
        LocationDataProcessingResult processingResult = new LocationDataProcessingResult(Result.SUCCESS, locationData);        
        return processingResult;        
    }
}

