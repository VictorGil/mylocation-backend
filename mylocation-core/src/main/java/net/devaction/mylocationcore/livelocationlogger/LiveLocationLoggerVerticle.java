package net.devaction.mylocationcore.livelocationlogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.vertxutilityextensions.config.ConfigValuesProvider;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LiveLocationLoggerVerticle extends AbstractVerticle implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(LiveLocationLoggerVerticle.class);

    private ConfigValuesProvider configValuesProvider;
    
    private String eventBusMulticastAddress;
    
    @Override
    public void afterPropertiesSet() throws Exception{
        if (eventBusMulticastAddress == null)
            eventBusMulticastAddress = configValuesProvider.getString("event_bus_multicast_address");        
    }
    
    @Override
    public void start(Future<Void> future){
        //this is just for logging purposes
        addEventBusMulticastAddressConsumer();
        
        future.complete();
    }
    
    //this is just for logging purposes
    void addEventBusMulticastAddressConsumer(){
        MessageConsumer<JsonObject> consumer = vertx.eventBus().consumer(eventBusMulticastAddress);
        log.info("Going to listen for messages on the event bus, the address is \"" + eventBusMulticastAddress + "\"");
        consumer.handler(message -> {
            log.debug(JsonObject.class.getSimpleName() + " message received on the " + eventBusMulticastAddress 
                    + " event bus address:\n" + message.body());
        });
    }
    
    @Override
    public void stop(){
        log.info(this.getClass().getSimpleName() + " verticle has been stopped");
    }
    
    //it may be useful when testing, it is not called by Spring
    public void setEventBusMulticastAddress(String eventBusMulticastAddress) {
        this.eventBusMulticastAddress = eventBusMulticastAddress;
    }
    
    //to be called by Spring
    public void setConfigValuesProvider(ConfigValuesProvider configValuesProvider) {
        this.configValuesProvider = configValuesProvider;
    }
}

