package net.devaction.mylocationcore.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocationcore.di.ConfValueProviderImpl;
import net.devaction.mylocationcore.di.VertxProviderImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class MainVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(MainVerticle.class);
    private JsonObject vertxConfig;
    
    private static final String CORE_SERVICE_CONFIG = "core_service_config";
    
    @Override
    public void start(){
        log.info("Starting " + this.getClass().getSimpleName());        

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.getConfig(asyncResult -> {
            if (asyncResult.failed()) {
                log.error("FATAL: Failed to retrieve configuration: " + asyncResult.cause(), asyncResult.cause());
                vertx.close(closeHandler -> {
                    log.info("Vert.x has been closed");
                });
            } else{
                vertxConfig = asyncResult.result();
                log.info("Retrieved configuration: " + vertxConfig);
                
                //This is a workaround, kind of
                ConfValueProviderImpl.setAppConfig(vertxConfig.getJsonObject(CORE_SERVICE_CONFIG));                
                VertxProviderImpl.setVertx(vertx);
                
                ApplicationContext appContext = new ClassPathXmlApplicationContext("conf/spring/beans.xml");
                VerticleBeans verticleBeans = (VerticleBeans) appContext.getBean("verticleBeans");
                ((ConfigurableApplicationContext) appContext).close();                
                
                //this is for the sun.misc.SignalHandler.handle method to be able to shutdown Vert.x
                MyLocationCoreMain.setVertx(vertx);
                
                deployVerticle(verticleBeans.getLocationDataServerVerticle());
                deployVerticle(verticleBeans.getWebServerVerticle());
                deployVerticle(verticleBeans.getLastKnownLocationVerticle());
            }
        });     
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
                log.error("Error when trying to deploy " + verticle.getClass().getSimpleName() +
                        ": " + asyncResult.cause(), asyncResult.cause());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });    
    }
   
    @Override
    public void stop(){
        log.info(this.getClass().getSimpleName() + " verticle has been stopped");
    }
}

