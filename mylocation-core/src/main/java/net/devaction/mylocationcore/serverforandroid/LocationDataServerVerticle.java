package net.devaction.mylocationcore.serverforandroid;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import net.devaction.mylocationcore.di.ConfValueProvider;
import net.devaction.mylocationcore.util.DecryptedValueProvider;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataServerVerticle extends AbstractVerticle implements InitializingBean{
    private static final Logger log = LogManager.getLogger(LocationDataServerVerticle.class);
 
    private ConfValueProvider confValueProvider;    
    private DecryptedValueProvider decryptedValueProvider;
    
    private String endPoint; 
    private Integer httpPort;    
    private String eventBusMulticastAddress;
    
    private String keyStorePassword;
    private String keyStoreFile;
    
    private LocationDataWebApiHandler handler;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (endPoint == null)
            endPoint = confValueProvider.getString("location_data_end_point");
        
        if (httpPort == null)
            httpPort = confValueProvider.getInteger("location_data_http_port");   
        
        if (eventBusMulticastAddress == null)
            eventBusMulticastAddress = confValueProvider.getString("event_bus_multicast_address"); 
        
        if (keyStorePassword == null){
            String keyStorePasswordEncrypted = confValueProvider.getString("keystore_password_encrypted");
            keyStorePassword = decryptedValueProvider.decrypt(keyStorePasswordEncrypted);
        }
                
        if (keyStoreFile == null)
            keyStoreFile = confValueProvider.getString("web_server_keystore_file");
    }
    
    @Override
    public void start(Future<Void> future){        
        log.info("Starting " + LocationDataServerVerticle.class.getSimpleName());
        
        addEventBusMessageConsumer();
        
        Router router = Router.router(vertx);        

        log.info("End point for the location data: " + endPoint);
        router.route(endPoint)
        
        //this enables the reading of the request body
        //see https://vertx.io/blog/some-rest-with-vert-x/
       .handler(BodyHandler.create());
        
        //IMPORTANT, this handler will process the request on a working thread
        router.post(endPoint).handler(handler);
        
        log.info("Added new route " + endPoint + " to the HTTP server, POST method");
        
        log.info("TCP port for the HTTP server to listen for location data requests: " + httpPort);
        createHttpServer(router, future, httpPort);
    }

    void addEventBusMessageConsumer(){
        MessageConsumer<String> consumer = vertx.eventBus().consumer(eventBusMulticastAddress);
        log.info("Going to listen for messages on the event bus, the multicast address is \"" + eventBusMulticastAddress + "\"");
        consumer.handler(message -> {
            log.debug("Message received on the event bus:\n" + message.body());
        });
    }
    
    private void createHttpServer(Router router, Future<Void> future, int port){
        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer(createHttpsOptions())
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
    
    private HttpServerOptions createHttpsOptions(){
        HttpServerOptions secureOptions = new HttpServerOptions().setSsl(true);
        JksOptions jksOptions = new JksOptions().setPath(keyStoreFile).setPassword(keyStorePassword);
        secureOptions.setKeyStoreOptions(jksOptions);
        secureOptions.setTrustOptions(jksOptions);        
        return secureOptions;
    }
    
    @Override
    public void stop(){
        log.info(this.getClass().getSimpleName() + " verticle has been stopped");
    }

    //to be called by Spring
    public void setConfValueProvider(ConfValueProvider confValueProvider) {
        this.confValueProvider = confValueProvider;
    }
    
    //to be called by Spring    
    public void setHandler(LocationDataWebApiHandler handler) {
        this.handler = handler;
    }
    
    //it may be useful when testing, it is not called by Spring
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    //it may be useful when testing, it is not called by Spring
    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    //it may be useful when testing, it is not called by Spring
    public void setEventBusMulticastAddress(String eventBusMulticastAddress) {
        this.eventBusMulticastAddress = eventBusMulticastAddress;
    }
    
    //it may be useful for testing, it is not called by Spring
    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    //to be called by Spring
    public void setDecryptedValueProvider(DecryptedValueProvider decryptedValueProvider) {
        this.decryptedValueProvider = decryptedValueProvider;
    }
    
    //it may be useful for testing, it is not called by Spring
    public void setKeyStoreFile(String keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }
}

