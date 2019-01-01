package net.devaction.mylocationcore.testeventbusverticle01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class TesterVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(TesterVerticle.class);
    
    private static final String TEST_ADDRESS_01 = "testAddress01";
        
    @Override
    public void start() throws Exception{
        final EventBus eventBus = vertx.eventBus();
        final Handler<Message<Object>> handler = new TesterVerticleHandler();
        
        MessageConsumer<Object> consumer = eventBus.consumer(TEST_ADDRESS_01, handler);
        
        consumer.completionHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("The " + handler.getClass().getSimpleName() + 
                        " has been successfully registered and it started listening for messages on " + TEST_ADDRESS_01 + " event bus address.");
            } else {
                log.error("FATAL: Registration of " + handler.getClass().getSimpleName() + " has failed.");
            }
        }); 
    }    
}

