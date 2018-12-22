package net.devaction.vertx.simpleverticle02;

import io.vertx.core.AbstractVerticle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class Verticle02 extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(Verticle02.class);
    
    static final String money = "money";
    
    @Override
    public void start(){
        log.info("Starting " + Verticle02.class.getSimpleName());
        try{
            super.start();
        } catch(Exception ex){
            log.error(ex.toString(), ex);
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
