package net.devaction.vertx.simpleverticle02;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class Verticle03 extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(Verticle03.class);
  
    @Override
    public void start(){
        log.info("Starting " + Verticle03.class.getSimpleName());
        try{
            super.start();
        } catch(Exception ex){
            log.error(ex.toString(), ex);
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
