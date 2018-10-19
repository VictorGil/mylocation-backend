package net.devaction.vertx.simpleverticle02;

import org.apache.logging.log4j.Logger;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class PublishSomeMoneyHandler implements Handler<Long>{
    private static final Logger log = LogManager.getLogger(PublishSomeMoneyHandler.class);
    
    private final int amount;
    private Vertx vertx;
    private String address;
    
    public PublishSomeMoneyHandler(int amount, Vertx vertx, String address){
        this.amount = amount;
        this.vertx = vertx;
        this.address = address;
    }
    
    @Override
    public void handle(Long event){
        log.info("Going to send " + amount + " USD.");
        vertx.eventBus().publish(address, new SomeMoney(amount, "USD").toJsonObject());        
    }    
}

