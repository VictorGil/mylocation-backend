package net.devaction.mylocationcore.lastknownlocationserverforfrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.vertxutilityextensions.config.ConfigValuesProvider;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class LastKnownLocationServerVerticleForFrontend extends AbstractVerticle implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationServerVerticleForFrontend.class);
    
    private ConfigValuesProvider configValuesProvider;
    
    private String lastKnownLocationAddressFrontend;
    private LastKnownLocationForFrontendHandler handler;
    
    @Override
    public void afterPropertiesSet() throws Exception{
        if (lastKnownLocationAddressFrontend == null)
            lastKnownLocationAddressFrontend = configValuesProvider
            .getString("event_bus_last_known_location_address_frontend");
        
        log.info("Vert.x event bus last known location address for the frontend: " 
                + lastKnownLocationAddressFrontend);
    }
    
    @Override
    public void start() throws Exception {
       
        MessageConsumer<JsonObject> consumer = vertx.eventBus().consumer(
                lastKnownLocationAddressFrontend, handler);
        
        consumer.completionHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("The " + LastKnownLocationServerVerticleForFrontend.class.getSimpleName() + 
                        " has been successfully registered and it started listening for messages on " 
                        + lastKnownLocationAddressFrontend + " event bus address.");
            } else {
                log.error("FATAL: Registration of " + LastKnownLocationServerVerticleForFrontend.class.getSimpleName() 
                        + " has failed.");
            }
        });        
    }

    public void setConfigValuesProvider(ConfigValuesProvider configValuesProvider) {
        this.configValuesProvider = configValuesProvider;
    }

    //this may be used when unit-testing
    public void setLastKnownLocationAddressFrontend(String lastKnownLocationAddressFrontend) {
        this.lastKnownLocationAddressFrontend = lastKnownLocationAddressFrontend;
    }

    public void setHandler(LastKnownLocationForFrontendHandler handler) {
        this.handler = handler;
    }
}

