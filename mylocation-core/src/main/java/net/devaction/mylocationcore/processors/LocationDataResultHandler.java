package net.devaction.mylocationcore.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataResultHandler implements Handler<AsyncResult<LocationDataProcessingResult>>{
    private static final Logger log = LoggerFactory.getLogger(LocationDataResultHandler.class);
    
    @Override
    public void handle(AsyncResult<LocationDataProcessingResult> asyncResult) {
        LocationDataProcessingResult processingResult = asyncResult.result();
        if (processingResult == null)
            log.error(asyncResult.cause().toString(), asyncResult.cause());       
        log.info("Result of the processing: " + processingResult);
    }
}

