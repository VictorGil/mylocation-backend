package net.devaction.mylocationcore.lastkownlocationverticleforfrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import net.devaction.mylocationcore.di.ConfValueProvider;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationVerticle extends AbstractVerticle implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationVerticle.class);

    private ConfValueProvider confValueProvider;
    
    private String eventBusMulticastAddress;
    
    @Override
    public void afterPropertiesSet() throws Exception{
        if (eventBusMulticastAddress == null)
            eventBusMulticastAddress = confValueProvider.getString("event_bus_multicast_address");        
    }
    
    @Override
    public void start(Future<Void> future){
        //this is just for logging purposes
        addEventBusMulticastAddressConsumer();
        
        future.complete();
    }
    
    //this is just for logging purposes
    void addEventBusMulticastAddressConsumer(){
        MessageConsumer<String> consumer = vertx.eventBus().consumer(eventBusMulticastAddress);
        log.info("Going to listen for messages on the event bus, the address is \"" + eventBusMulticastAddress + "\"");
        consumer.handler(message -> {
            log.debug("Message received on the " + eventBusMulticastAddress + " event bus address:\n" + message.body());
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
    public void setConfValueProvider(ConfValueProvider confValueProvider) {
        this.confValueProvider = confValueProvider;
    }
}

