package net.devaction.mylocationcore.serverforandroid;

import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocation.locationpersistenceapi.protobuf.LocationPersistenceRequest;
import net.devaction.mylocation.vertxutilityextensions.config.ConfigValuesProvider;
import net.devaction.mylocation.vertxutilityextensions.config.VertxProvider;
import net.devaction.mylocationcore.persistence.LocationPersistenceRequestProvider;
import net.devaction.mylocationcore.persistence.ResponseFromServerHandler;
import net.devaction.mylocationcore.util.LocationDataUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataWebApiHandler implements Handler<RoutingContext>, InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(LocationDataWebApiHandler.class);
    public static final int HTTP_OK_200 = 200;
    
    private Vertx vertx;
    private VertxProvider vertxProvider;
    
    private String eventBusMulticastAddress; 
    private ConfigValuesProvider configValuesProvider;
    
    private LocationPersistenceRequestProvider locationPersistenceRequestProvider;
    private String locationPersistAddress;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if (vertx == null)
            vertx = vertxProvider.provide();

        if (eventBusMulticastAddress == null)
            eventBusMulticastAddress = configValuesProvider.getString("event_bus_multicast_address");
        
        if (locationPersistAddress == null)
            locationPersistAddress = configValuesProvider.getString("event_bus_location_persist_address");
    }        
    
    @Override
    public void handle(RoutingContext routingContext) {
        
        JsonObject locationDataJsonObject = routingContext.getBodyAsJson();
        log.trace("Body of the http request received as JSON object: " + locationDataJsonObject);
        
        routingContext.response().setStatusCode(HTTP_OK_200).end();
        
        //TO DO?: maybe we could check that the JsonObject is as expected before publishing it
        vertx.eventBus().publish(eventBusMulticastAddress, locationDataJsonObject);
        
        LocationData locationData = LocationDataUtil.constructObject(locationDataJsonObject);
        LocationPersistenceRequest persistenceRequest = locationPersistenceRequestProvider.provide(locationData);
        
        Buffer buffer = Buffer.buffer(); 
        buffer.appendBytes(persistenceRequest.toByteArray());
        
        log.trace("Going to send a " + persistenceRequest.getClass().getSimpleName() +  " message to " 
                + locationPersistAddress + " address.");
        vertx.eventBus().send(locationPersistAddress, buffer, new ResponseFromServerHandler());             
    }
    
    //it may be useful when testing
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }
    
    public void setVertxProvider(VertxProvider vertxProvider) {
        this.vertxProvider = vertxProvider;
    }

    public void setConfigValuesProvider(ConfigValuesProvider configValuesProvider) {
        this.configValuesProvider = configValuesProvider;
    }

    public void setLocationPersistenceRequestProvider(
            LocationPersistenceRequestProvider locationPersistenceRequestProvider) {
        this.locationPersistenceRequestProvider = locationPersistenceRequestProvider;
    }

    //to be used just when unit testing
    public void setEventBusMulticastAddress(String eventBusMulticastAddress) {
        this.eventBusMulticastAddress = eventBusMulticastAddress;
    }
}

