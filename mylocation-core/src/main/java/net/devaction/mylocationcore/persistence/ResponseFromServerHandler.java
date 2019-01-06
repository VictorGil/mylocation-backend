package net.devaction.mylocationcore.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import net.devaction.mylocation.locationpersistenceapi.protobuf.LocationPersistenceResponse;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class ResponseFromServerHandler implements Handler<AsyncResult<Message<Buffer>>>{
    private static final Logger log = LoggerFactory.getLogger(ResponseFromServerHandler.class);
    
    @Override
    public void handle(AsyncResult<Message<Buffer>> asyncResult){
        if (asyncResult.succeeded()){
            Message<Buffer> message = asyncResult.result();
            Buffer buffer = message.body();
            log.trace("Received reply from the server, number of bytes in the body of the message: " + buffer.length());
            
            LocationPersistenceResponse response = null;
            try{
                response = LocationPersistenceResponse.parseFrom(buffer.getBytes());
            } catch (InvalidProtocolBufferException ex){
                String errorMessage = "Unable to parse " + LocationPersistenceResponse.class + " object: " 
                        + ex;
                log.error(errorMessage, ex);
                return;
            }
            
            log.trace(response.getClass().getSimpleName() + " proto object received from the server:\n" 
                    + response);
            
        } else{
            Throwable throwable = asyncResult.cause();
            log.error("The request could not be processed by the server: " + throwable, throwable);
        }        
    }
}

