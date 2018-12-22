package net.devaction.vertx.simpleverticle02;

import io.vertx.core.AbstractVerticle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class Verticle01 extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(Verticle01.class);
  
    @Override
    public void start(){
        log.info("Starting " + Verticle01.class.getSimpleName());
        
        try{
            super.start();
        } catch(Exception ex){
            log.error(ex.toString(), ex);
            throw new RuntimeException(ex);
        }
        deployVerticle02();
        deployVerticle03();
        
        int seconds = 10;
        log.info("vertx will be closed after " + seconds + " seconds");
        vertx.setTimer(1000 * seconds, timerHandler -> {
            vertx.close(closeHandler -> {
                log.info("vertx has been closed");
            });
        });    
    }
    
    public void deployVerticle02(){
        log.info("Going to deploy " + Verticle02.class.getSimpleName());
        vertx.deployVerticle(new Verticle02(), asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("Successfully deployed " +  
                        Verticle02.class.getSimpleName() + ". Result: " + asyncResult.result());
            } else {
                log.error("Error when trying to deploy " + Verticle02.class.getSimpleName());
            }
        });    
    }
    
    public void deployVerticle03(){
        log.info("Going to deploy " + Verticle03.class.getSimpleName());
        vertx.deployVerticle(new Verticle03(), asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("Successfully deployed " +  
                        Verticle03.class.getSimpleName() + ". Result: " + asyncResult.result());
            } else {
                log.error("Error when trying to deploy " + Verticle03.class.getSimpleName());
            }
        });    
    }
    
    @Override
    public void stop() throws Exception {
        super.stop();
        log.info("Verticle has been stopped");
    }
}

