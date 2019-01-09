package net.devaction.mylocationcore.lastknownlocationserverforfrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class LastKnownLocationJsonResponseProvider{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationJsonResponseProvider.class);
    
    public JsonObject provide(LastKnownLocationResponse protoResponse){
        String jsonString = null;        
        try{            
            jsonString = JsonFormat.printer().print(protoResponse);
        } catch(InvalidProtocolBufferException ex){
            log.error(ex.toString(), ex);
            return null;
        }
        
        //TO DO: The type of all the properties in this JsonObject is String, I do not like that
        JsonObject jsonObject = new JsonObject(jsonString);
        
        return jsonObject;
    }
}

