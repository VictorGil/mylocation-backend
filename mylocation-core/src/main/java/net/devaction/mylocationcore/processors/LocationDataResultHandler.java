package net.devaction.mylocationcore.processors;

import org.apache.logging.log4j.Logger;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataResultHandler implements Handler<AsyncResult<LocationDataProcessingResult>>{
    private static final Logger log = LogManager.getLogger(LocationDataResultHandler.class);
    
    @Override
    public void handle(AsyncResult<LocationDataProcessingResult> asyncResult) {
        LocationDataProcessingResult processingResult = asyncResult.result();
        if (processingResult == null)
            log.error(asyncResult.cause(), asyncResult.cause());       
        log.info("Result of the processing: " + processingResult);
    }
}

