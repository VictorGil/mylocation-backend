package net.devaction.mylocationcore.serverforwebbrowser;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import net.devaction.mylocationcore.di.ConfValueProvider;
import net.devaction.mylocationcore.util.DecryptedValueProvider;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class WebServerVerticle extends AbstractVerticle implements InitializingBean{
    private static final Logger log = LogManager.getLogger(WebServerVerticle.class);
    
    private ConfValueProvider confValueProvider; 
    private DecryptedValueProvider decryptedValueProvider;
    
    private String keyStorePassword;
    private String keyStoreFile;
    private Integer httpPort;
    private String eventBusMulticastAddress;
    
    private final HttpServerOptions secureOptions = new HttpServerOptions();

    @Override
    public void afterPropertiesSet() throws Exception {
        if (keyStorePassword == null){
            String keyStorePasswordEncrypted = confValueProvider.getString("keystore_password_encrypted");
            keyStorePassword = decryptedValueProvider.decrypt(keyStorePasswordEncrypted);
        }
                
        if (keyStoreFile == null)
            keyStoreFile = confValueProvider.getString("web_server_keystore_file");
        
        if (httpPort == null)
            httpPort = confValueProvider.getInteger("web_server_http_port");
        
        if (eventBusMulticastAddress == null)
            eventBusMulticastAddress = confValueProvider.getString("event_bus_multicast_address");
        
        secureOptions.setKeyStoreOptions(new JksOptions().setPath(
                //this file must be in the classpath
                keyStoreFile)
                .setPassword(keyStorePassword)).setSsl(true);
    }
    
    @Override
    public void start(Future<Void> future){
        log.info("Starting " + WebServerVerticle.class.getSimpleName());

        Router webRouter = Router.router(vertx);
        
        String canonicalPath = null;
        try{
            canonicalPath = new File("").getCanonicalPath();
            log.info("Canonical path: " + canonicalPath);
        } catch(IOException ex){
            log.error(ex, ex);
        }        
       
        webRouter.route("/eventbusbridge/*").handler(createSockJSBridgeHandler());
        webRouter.route("/*").handler(StaticHandler.create("webroot").setCachingEnabled(false));
        
        vertx.createHttpServer(secureOptions).requestHandler(webRouter::accept)
        .listen(httpPort,
                result -> {
                    if (result.succeeded()) {
                        log.info("HTTP server started for Web browser clients, port: " + httpPort);  
                        future.complete();
                    } else{
                        log.error("Could not start HTTP server for Web browser clients.");  
                        future.fail(result.cause());
                    }
                }
        );                 
    }

    SockJSHandler createSockJSBridgeHandler(){
        SockJSHandlerOptions handlerOptions = new SockJSHandlerOptions().setHeartbeatInterval(5000);
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx, handlerOptions);
                
        BridgeOptions bridgeOptions = new BridgeOptions()
                .addOutboundPermitted(new PermittedOptions().setAddress(eventBusMulticastAddress))
                .addInboundPermitted(new PermittedOptions().setAddress(eventBusMulticastAddress));

        sockJSHandler.bridge(bridgeOptions, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED)
                log.info("A socket was created.");

            if (event.type() == BridgeEventType.PUBLISH)
                log.info("A message has been published to the event bus bridge.");
    
            event.complete(true);
        });
        
        return sockJSHandler;
    }    
    
    @Override
    public void stop(){
        log.info("Verticle " + this.getClass().getSimpleName() + " has been stopped");
    }

    //to be called by Spring
    public void setConfValueProvider(ConfValueProvider confValueProvider) {
        this.confValueProvider = confValueProvider;
    }
    
    //to be called by Spring
    public void setDecryptedValueProvider(DecryptedValueProvider decryptedValueProvider) {
        this.decryptedValueProvider = decryptedValueProvider;
    }

    //it may be useful for testing, it is not called by Spring
    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    //it may be useful for testing, it is not called by Spring
    public void setKeyStoreFile(String keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }

    //it may be useful for testing, it is not called by Spring
    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    //it may be useful for testing, it is not called by Spring
    public void setEventBusMulticastAddress(String eventBusMulticastAddress) {
        this.eventBusMulticastAddress = eventBusMulticastAddress;
    }
}

