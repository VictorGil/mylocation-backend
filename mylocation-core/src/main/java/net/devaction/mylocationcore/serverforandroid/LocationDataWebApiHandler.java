package net.devaction.mylocationcore.serverforandroid;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.devaction.mylocationcore.di.ConfValueProvider;
import net.devaction.mylocationcore.di.VertxProvider;
import net.devaction.mylocationcore.main.MainVerticle;
import net.devaction.mylocationcore.processors.LocationDataHandler;
import net.devaction.mylocationcore.processors.LocationDataProcessor;
import net.devaction.mylocationcore.processors.LocationDataResultHandler;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataWebApiHandler implements Handler<RoutingContext>, InitializingBean{
    private static final Logger log = LogManager.getLogger(LocationDataWebApiHandler.class);
    public static final int HTTP_OK_200 = 200;
    
    private Vertx vertx;
    private VertxProvider vertxProvider;
    
    //private LocationDataHandler handler;
    private LocationDataProcessor processor;
    private LocationDataResultHandler resultHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (vertx == null)
            vertx = vertxProvider.provide();        
    }
    
    //private String eventBusMulticastAddress;
    
    //private final LocationDataProcessor locationDataProcessor;
    
    //public LocationDataWebApiHandler(Vertx vertx){        
    //    this.vertx = vertx;
        //final String EVENT_BUS_MULTICAST_ADDRESS = MainVerticle.getAppConfig().getJsonObject("app_config").getString("event_bus_multicast_address");        
        //locationDataProcessor = new LocationDataProcessor(vertx.eventBus(), EVENT_BUS_MULTICAST_ADDRESS);
    //}
    
    @Override
    public void handle(RoutingContext routingContext) {
        
        JsonObject locationDataJsonObject = routingContext.getBodyAsJson();
        log.trace("Body of the http request received as JSON object: " + locationDataJsonObject);
        
        routingContext.response().setStatusCode(HTTP_OK_200).end();   
        
        //we cannot use Spring to inject this handler, we need to create a new instance because of the 
        //locationDataJsonObject whose value is not constant
        LocationDataHandler handler = new LocationDataHandler(locationDataJsonObject, processor);
        
        final boolean notOrdered = false;
        //LocationDataResultHandler resultHandler = new LocationDataResultHandler();
        vertx.executeBlocking(handler, notOrdered, resultHandler);        
    }
    
    //it may be useful when testing
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }
    
    public void setVertxProvider(VertxProvider vertxProvider) {
        this.vertxProvider = vertxProvider;
    }
    
    public void setResultHandler(LocationDataResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    public void setProcessor(LocationDataProcessor processor) {
        this.processor = processor;
    }
}

