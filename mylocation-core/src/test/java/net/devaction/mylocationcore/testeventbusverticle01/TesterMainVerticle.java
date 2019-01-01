package net.devaction.mylocationcore.testeventbusverticle01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class TesterMainVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(TesterMainVerticle.class);

    @Override
    public void start() throws Exception{
        TesterMain.setVertx(vertx); 
        deployVerticle(new TesterVerticle());
    }
    
    private void deployVerticle(Verticle verticle){
        if (verticle == null){
            log.error("The verticle which was supposed to be deployed is null, nothing to do.");
            return;
        }
        
        log.info("Going to deploy " + verticle.getClass().getSimpleName());
        vertx.deployVerticle(verticle, asyncResult -> {
            if (asyncResult.succeeded()){
                log.info("Successfully deployed " +  
                        verticle.getClass().getSimpleName() + ". Result: " + asyncResult.result());
            } else{
                log.error("FATAL: Error when trying to deploy " + verticle.getClass().getSimpleName() +
                        ": " + asyncResult.cause(), asyncResult.cause());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });    
    }
}

