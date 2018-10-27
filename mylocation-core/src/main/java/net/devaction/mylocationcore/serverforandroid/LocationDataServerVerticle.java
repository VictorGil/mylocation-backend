package net.devaction.mylocationcore.serverforandroid;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import net.devaction.mylocationcore.di.ConfValueProvider;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataServerVerticle extends AbstractVerticle implements InitializingBean{
    private static final Logger log = LogManager.getLogger(LocationDataServerVerticle.class);
 
    private ConfValueProvider confValueProvider;    
    private String endPoint; 
    private Integer httpPort;    
    private String eventBusMulticastAddress;
    
    private LocationDataWebApiHandler handler;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (endPoint == null)
            endPoint = confValueProvider.getString("location_data_end_point");
        
        if (httpPort == null)
            httpPort = confValueProvider.getInteger("location_data_http_port");   
        
        if (eventBusMulticastAddress == null)
            eventBusMulticastAddress = confValueProvider.getString("event_bus_multicast_address"); 
    }
    
    @Override
    public void start(Future<Void> future){        
        log.info("Starting " + LocationDataServerVerticle.class.getSimpleName());
        
        addEventBusMessageConsumer();
        
        Router router = Router.router(vertx);
        

        //final String LOCATION_DATA_END_POINT = 
        //        MainVerticle.getAppConfig().getJsonObject("app_config").getString("location_data_end_point");
        log.info("End point for the location data: " + endPoint);
        router.route(endPoint)
        
        //this enables the reading of the request body
        //see https://vertx.io/blog/some-rest-with-vert-x/
       .handler(BodyHandler.create());
        
        //IMPORTANT, this handler will process the request on a working thread
        router.post(endPoint).handler(handler);
        
        log.info("Added new route " + endPoint + " to the HTTP server, POST method");
        
        //httpPort = MainVerticle.getAppConfig().getJsonObject("app_config").getInteger("location_data_http_port");
        log.info("TCP port for the HTTP server to listen for location data requests: " + httpPort);
        createHttpServer(router, future, httpPort);
    }

    void addEventBusMessageConsumer(){
        //final String eventBusMulticastAddress = MainVerticle.getAppConfig().getJsonObject("app_config").getString("event_bus_multicast_address");
        MessageConsumer<String> consumer = vertx.eventBus().consumer(eventBusMulticastAddress);
        log.info("Going to listen for messages on the event bus, the multicast address is \"" + eventBusMulticastAddress + "\"");
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

    //to be called by Spring
    public void setConfValueProvider(ConfValueProvider confValueProvider) {
        this.confValueProvider = confValueProvider;
    }

    //it may be useful when testing
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    //it may be useful when testing
    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    //it may be useful when testing
    public void setEventBusMulticastAddress(String eventBusMulticastAddress) {
        this.eventBusMulticastAddress = eventBusMulticastAddress;
    }

    public void setHandler(LocationDataWebApiHandler handler) {
        this.handler = handler;
    }
}

