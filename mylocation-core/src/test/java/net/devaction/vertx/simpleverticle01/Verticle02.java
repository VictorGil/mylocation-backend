package net.devaction.vertx.simpleverticle01;

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
  
    @Override
    public void start() {
        log.info("Starting " + Verticle02.class.getSimpleName());
        try{
            super.start();
        } catch(Exception ex){
            log.error(ex.toString(), ex);
            throw new RuntimeException(ex);
        }        
    }
}

