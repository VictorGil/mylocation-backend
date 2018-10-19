package net.devaction.mylocationcore.main;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocationcore.serverforandroid.LocationDataServerVerticle;
import net.devaction.mylocationcore.serverforwebbrowser.WebServerVerticle;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class MainVerticle extends AbstractVerticle{
    private static final Logger log = LogManager.getLogger(MainVerticle.class);

    private static JsonObject appConfig;
    
    @Override
    public void start(){
        log.info("Starting " + MainVerticle.class.getSimpleName());        
        
        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.getConfig(asyncResult -> {
            if (asyncResult.failed()) {
                log.fatal("Failed to retrieve configuration: " + asyncResult.cause(), asyncResult.cause());
                log.info("Terminating application");
                System.exit(1);
            } else{
                appConfig = asyncResult.result();
                log.info("Retrieved configuration: " + appConfig);
                
                //this is for the sun.misc.SignalHandler.handle method to be able to shutdown Vert.x
                MyLocationCoreMain.setVertx(vertx);
                
                deployLocationDataServerVerticle();
                deployWebServerVerticle();
            }
          });     
    }
    
    public void deployLocationDataServerVerticle(){
        log.info("Going to deploy " + LocationDataServerVerticle.class.getSimpleName());
        vertx.deployVerticle(new LocationDataServerVerticle(), asyncResult -> {
            if (asyncResult.succeeded()){
                log.info("Successfully deployed " +  
                        LocationDataServerVerticle.class.getSimpleName() + ". Result: " + asyncResult.result());
            } else{
                log.error("Error when trying to deploy " + LocationDataServerVerticle.class.getSimpleName());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });    
    }    

    public void deployWebServerVerticle(){
        log.info("Going to deploy " + WebServerVerticle.class.getSimpleName());
        vertx.deployVerticle(new WebServerVerticle(), asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("Successfully deployed " +  
                        WebServerVerticle.class.getSimpleName() + ". Result: " + asyncResult.result());
            } else {
                log.error("Error when trying to deploy " + WebServerVerticle.class.getSimpleName());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });    
    } 

    @Override
    public void stop(){
        log.info("Verticle has been stopped");
    }

    public static JsonObject getAppConfig() {
        return appConfig;
    }
}

