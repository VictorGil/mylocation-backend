package net.devaction.mylocationcore.lastknownlocationserverforfrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationRequest;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class ResponseFromLastKnownLocationServerHandler implements Handler<AsyncResult<Message<Buffer>>>{
    private static final Logger log = LoggerFactory.getLogger(ResponseFromLastKnownLocationServerHandler.class);
    
    //original request received from the frontend
    private final Message<JsonObject> originalRequest;

    private LastKnownLocationJsonResponseProvider jsonResponseProvider;
    
    public ResponseFromLastKnownLocationServerHandler( 
            LastKnownLocationJsonResponseProvider jsonResponseProvider,
            Message<JsonObject> originalRequest){
        this.jsonResponseProvider = jsonResponseProvider;
        this.originalRequest = originalRequest;            
    }
    
    @Override
    public void handle(AsyncResult<Message<Buffer>> asyncResult) {
        if (asyncResult.succeeded()){
            Message<Buffer> message = asyncResult.result();
            Buffer buffer = message.body();
            
            LastKnownLocationResponse protoResponse = null;            
            try{
                protoResponse = LastKnownLocationResponse.parseFrom(buffer.getBytes());
            } catch(InvalidProtocolBufferException ex){
                log.error(ex.toString(), ex);
                return;
            }
            
            JsonObject finalResponse = jsonResponseProvider.provide(protoResponse);
            if (finalResponse != null)
                originalRequest.reply(finalResponse);            
        } else{
            Throwable throwable = asyncResult.cause();
            log.error("The (protobuf) " + LastKnownLocationRequest.class.getSimpleName()  + 
                    " could not be processed by the server: " + throwable, throwable);
        }
        
    }

    public void setJsonResponseProvider(LastKnownLocationJsonResponseProvider jsonResponseProvider) {
        this.jsonResponseProvider = jsonResponseProvider;
    }    
}

