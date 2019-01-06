package net.devaction.mylocationcore.serverforandroid;

import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.devaction.mylocation.vertxutilityextensions.config.VertxProvider;
import net.devaction.mylocationcore.processors.LocationDataHandler;
import net.devaction.mylocationcore.processors.LocationDataProcessor;
import net.devaction.mylocationcore.processors.LocationDataResultHandler;

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
    
    private LocationDataProcessor processor;
    private LocationDataResultHandler resultHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (vertx == null)
            vertx = vertxProvider.provide();        
    }
    
    @Override
    public void handle(RoutingContext routingContext) {
        
        JsonObject locationDataJsonObject = routingContext.getBodyAsJson();
        log.trace("Body of the http request received as JSON object: " + locationDataJsonObject);
        
        routingContext.response().setStatusCode(HTTP_OK_200).end();   
        
        //we do not use Spring to inject this handler, to be on the safe side, 
        //we create a new instance instead because the 
        //locationDataJsonObject whose value is not constant
        LocationDataHandler handler = new LocationDataHandler(locationDataJsonObject, processor);
        
        final boolean notOrdered = false;
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

