package net.devaction.mylocationcore.lastknownlocationserverforfrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationRequest;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class LastKnownLocationRequestProvider{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationRequestProvider.class);
    
    public LastKnownLocationRequest provide(JsonObject json){
        LastKnownLocationRequest.Builder requestBuilder = LastKnownLocationRequest.newBuilder();
        
        if (json == null)
            return requestBuilder.build();
        
        Long timestamp = json.getLong("timestamp");
        if (timestamp == null || timestamp < 1)
            return requestBuilder.build();
        
        requestBuilder.setTimestamp(timestamp);
        
        return requestBuilder.build();
    }
}

