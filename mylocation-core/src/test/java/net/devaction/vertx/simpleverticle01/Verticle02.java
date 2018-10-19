package net.devaction.vertx.simpleverticle01;

import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018
 */
public class Verticle02 extends AbstractVerticle{
    private static final Logger log = LogManager.getLogger(Verticle02.class);
  
    @Override
    public void start() {
        log.info("Starting " + Verticle02.class.getSimpleName());
        try{
            super.start();
        } catch(Exception ex){
            log.error(ex, ex);
            throw new RuntimeException(ex);
        }        
    }
}

