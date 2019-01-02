package net.devaction.mylocationcore.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.eventbus.EventBus;
import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocation.vertxutilityextensions.config.ConfigValuesProvider;
import net.devaction.mylocation.vertxutilityextensions.config.VertxProvider;
import net.devaction.mylocationcore.sharedenums.Result;
import net.devaction.mylocationcore.util.LocationDataUtil;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataProcessor implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(LocationDataProcessor.class);
    
    private EventBus eventBus;
    private VertxProvider vertxProvider;
    
    private String eventBusMulticastAddress; 
    private ConfigValuesProvider configValuesProvider;
    
    public LocationDataProcessingResult process(LocationData locationData){       
        log.debug("Going to process LocationData: " + locationData);
        
        eventBus.publish(eventBusMulticastAddress, LocationDataUtil.convertToJsonString(locationData));       
        
        log.debug("Processing of LocationData finished: " + locationData);
     
        LocationDataProcessingResult processingResult = new LocationDataProcessingResult(Result.SUCCESS, locationData);        
        return processingResult;        
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (eventBus == null)
            eventBus = vertxProvider.provide().eventBus();
        if (eventBusMulticastAddress == null)
            eventBusMulticastAddress = configValuesProvider.getString("event_bus_multicast_address"); 
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setVertxProvider(VertxProvider vertxProvider) {
        this.vertxProvider = vertxProvider;
    }

    public void setEventBusMulticastAddress(String eventBusMulticastAddress) {
        this.eventBusMulticastAddress = eventBusMulticastAddress;
    }

    public void setConfigValuesProvider(final ConfigValuesProvider configValuesProvider) {
        this.configValuesProvider = configValuesProvider;
    }
}

