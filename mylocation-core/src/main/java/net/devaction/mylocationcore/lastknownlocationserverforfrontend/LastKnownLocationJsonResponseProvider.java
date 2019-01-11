package net.devaction.mylocationcore.lastknownlocationserverforfrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;
import net.devaction.mylocation.lastknownlocationapi.protobuf.Status;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class LastKnownLocationJsonResponseProvider{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationJsonResponseProvider.class);
    
    public JsonObject provide(LastKnownLocationResponse protoResponse){        
        if (protoResponse.hasStatus()){
            Status status = protoResponse.getStatus();
            if (status == Status.FAILURE){
                log.error("The request to the last known location server failed: " + 
                    protoResponse.getErrorMessage() + ".\nNot going to send anything to the frontend");
                return null;
            }                
        }
        
        JsonObject jsonObject = new JsonObject();
        
        if (protoResponse.hasLatitude())
            jsonObject.put("latitude", protoResponse.getLatitude());
        
        if (protoResponse.hasLongitude())
            jsonObject.put("longitude", protoResponse.getLongitude());
        
        if (protoResponse.hasHorizontalAccuracy())
            jsonObject.put("horizontalAccuracy", protoResponse.getHorizontalAccuracy());
        
        if (protoResponse.hasAltitude())
            jsonObject.put("altitude", protoResponse.getAltitude());
        
        if (protoResponse.hasVerticalAccuracy())
            jsonObject.put("verticalAccuracy", protoResponse.getVerticalAccuracy());
        
        if (protoResponse.hasTimeChecked() && protoResponse.getTimeChecked() > 0)
            jsonObject.put("timeChecked", protoResponse.getTimeChecked());
        
        if (protoResponse.hasTimeMeasured() && protoResponse.getTimeMeasured() > 0)
            jsonObject.put("timeMeasured", protoResponse.getTimeMeasured());
        
        return jsonObject;
    }
}

