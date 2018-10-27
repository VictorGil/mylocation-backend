package net.devaction.mylocationcore.di;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.Vertx;

/**
 * @author Víctor Gil
 * since October 2018 
 */
public class VertxProviderImpl implements VertxProvider{
    private static final Logger log = LogManager.getLogger(VertxProviderImpl.class);

    private static Vertx vertx;
    
    @Override
    public Vertx provide(){
        if (vertx == null){
            String errorMessage = "Vertx is null";
            log.fatal(errorMessage);
            throw new RuntimeException(errorMessage);
        }            
        return vertx;
    }

    public static void setVertx(Vertx vertx) {
        VertxProviderImpl.vertx = vertx;
    }   
}

