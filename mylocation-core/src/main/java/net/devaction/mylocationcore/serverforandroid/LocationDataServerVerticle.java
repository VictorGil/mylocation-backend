package net.devaction.mylocationcore.serverforandroid;

import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import net.devaction.mylocationcore.main.MainVerticle;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataServerVerticle extends AbstractVerticle{
    private static final Logger log = LogManager.getLogger(LocationDataServerVerticle.class);
    
    @Override
    public void start(Future<Void> future){        
        log.info("Starting " + LocationDataServerVerticle.class.getSimpleName());
        
        addEventBusMessageConsumer();
        
        Router router = Router.router(vertx);
        

        final String LOCATION_DATA_END_POINT = 
                MainVerticle.getAppConfig().getJsonObject("app_config").getString("location_data_end_point");
        log.info("End point for the location data: " + LOCATION_DATA_END_POINT);
        router.route(LOCATION_DATA_END_POINT)
        
        //this enables the reading of the request body
        //see https://vertx.io/blog/some-rest-with-vert-x/
       .handler(BodyHandler.create());
        
        //IMPORTANT, this handler will process the request on a working thread
        router.post(LOCATION_DATA_END_POINT).handler(new LocationDataWebApiHandler(vertx));
        
        log.info("Added new route " + LOCATION_DATA_END_POINT + " to the HTTP server, POST method");
        
        final int HTTP_PORT = MainVerticle.getAppConfig().getJsonObject("app_config").getInteger("location_data_http_port");
        log.info("TCP port for the HTTP server to listen for location data requests: " + HTTP_PORT);
        createHttpServer(router, future, HTTP_PORT);
    }

    void addEventBusMessageConsumer(){
        final String EVENT_BUS_MULTICAST_ADDRESS = MainVerticle.getAppConfig().getJsonObject("app_config").getString("event_bus_multicast_address");
        MessageConsumer<String> consumer = vertx.eventBus().consumer(EVENT_BUS_MULTICAST_ADDRESS);
        log.info("Going to listen for messages on the event bus, the multicast address is \"" + EVENT_BUS_MULTICAST_ADDRESS + "\"");
        consumer.handler(message -> {
            log.debug("Message received on the event bus:\n" + message.body());
        });
    }
    
    private void createHttpServer(Router router, Future<Void> future, int port){
        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(port,
                result -> {
                    if (result.succeeded()) {
                        log.info("HTTP server started, port: " + port);  
                        future.complete();
                    } else{
                        log.error("Could not start HTTP server");  
                        future.fail(result.cause());
                    }
                }
        );    
    }
    
    @Override
    public void stop(){
        log.info(this.getClass().getSimpleName() + " verticle has been stopped");
    }
}

