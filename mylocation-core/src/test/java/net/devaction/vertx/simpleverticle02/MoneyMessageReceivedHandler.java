package net.devaction.vertx.simpleverticle02;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class MoneyMessageReceivedHandler implements Handler<Message<JsonObject>>{
    private static final Logger log = LoggerFactory.getLogger(MoneyMessageReceivedHandler.class);
    
   @Override
    public void handle(Message<JsonObject> message) {
        log.info("Message received: " + message.toString());
        log.info("Message headers: " + message.headers());
        log.info("Message body: " + message.body());
        JsonObject someMoneyJsonObject = message.body();
        SomeMoney someMoney = SomeMoney.getNewInstance(someMoneyJsonObject);
        log.info("Reconstructed SomeMoney object received: " + someMoney + "\n");       
    }    
}

