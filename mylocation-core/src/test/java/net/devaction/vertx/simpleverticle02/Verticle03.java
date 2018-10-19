package net.devaction.vertx.simpleverticle02;

import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class Verticle03 extends AbstractVerticle{
    private static final Logger log = LogManager.getLogger(Verticle03.class);
  
    @Override
    public void start(){
        log.info("Starting " + Verticle03.class.getSimpleName());
        try{
            super.start();
        } catch(Exception ex){
            log.error(ex, ex);
            throw new RuntimeException(ex);
        }
        
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(Verticle02.money, new MoneyMessageReceivedHandler());
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        log.info("Verticle has been stopped");
    }
}
