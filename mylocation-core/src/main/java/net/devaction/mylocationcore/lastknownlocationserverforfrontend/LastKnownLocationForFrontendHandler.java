package net.devaction.mylocationcore.lastknownlocationserverforfrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationRequest;
import net.devaction.mylocation.vertxutilityextensions.config.ConfigValuesProvider;
import net.devaction.mylocation.vertxutilityextensions.config.VertxProvider;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class LastKnownLocationForFrontendHandler implements Handler<Message<JsonObject>>, 
        InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationForFrontendHandler.class);

    private LastKnownLocationRequestProvider requestProvider;
    
    private ConfigValuesProvider configValuesProvider;
    private Vertx vertx;
    private VertxProvider vertxProvider;
    
    private String lastKnownLocationAddress;    

    @Override
    public void afterPropertiesSet() throws Exception{        
        vertx = vertxProvider.provide();
        
        if (lastKnownLocationAddress == null)
            lastKnownLocationAddress = configValuesProvider.getString("event_bus_last_known_location_address");
    }
    
    @Override
    public void handle(Message<JsonObject> message) {
        JsonObject json = message.body();
        LastKnownLocationRequest protoRequest = requestProvider.provide(json);
        
        Buffer buffer = Buffer.buffer(); 
        buffer.appendBytes(protoRequest.toByteArray());
        
        ResponseFromLastKnownLocationServerHandler secondHandler = 
                new ResponseFromLastKnownLocationServerHandler(
                        new LastKnownLocationJsonResponseProvider(), message);
        
        vertx.eventBus().send(lastKnownLocationAddress, buffer, secondHandler);        
    }

    public void setRequestProvider(LastKnownLocationRequestProvider requestProvider) {
        this.requestProvider = requestProvider;
    }

    public void setConfigValuesProvider(ConfigValuesProvider configValuesProvider) {
        this.configValuesProvider = configValuesProvider;
    }

    public void setVertxProvider(VertxProvider vertxProvider) {
        this.vertxProvider = vertxProvider;
    }
}

