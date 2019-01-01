package net.devaction.mylocationcore.testeventbusverticle01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class TesterVerticleHandler implements Handler<Message<Object>>{
    private static final Logger log = LoggerFactory.getLogger(TesterVerticleHandler.class);

    @Override
    public void handle(Message<Object> message) {
        Object body = message.body();
        
        log.info("The object in the message body is of type: " + body.getClass());
        log.info("Message body:\n" + body); 
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.put("status", "SUCCESS");
        
        message.reply(jsonResponse);
    }
}

