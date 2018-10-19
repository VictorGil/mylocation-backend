package net.devaction.vertx.simpleverticle02;

import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class Verticle02 extends AbstractVerticle{
    private static final Logger log = LogManager.getLogger(Verticle02.class);
    
    static final String money = "money";
    
    @Override
    public void start(){
        log.info("Starting " + Verticle02.class.getSimpleName());
        try{
            super.start();
        } catch(Exception ex){
            log.error(ex, ex);
            throw new RuntimeException(ex);
        }

        publishMessages();           
        }
        
    public void publishMessages(){
        for (int i = 0; i <= 5; i++){
            PublishSomeMoneyHandler publishSomeMoneyHandler = 
                    new PublishSomeMoneyHandler(1000 + i, vertx, money);                
            vertx.setTimer(1000 + 1000 * i, publishSomeMoneyHandler);
        }
    }
    
    @Override
    public void stop() throws Exception {
        super.stop();
        log.info("Verticle has been stopped");
    }
}
