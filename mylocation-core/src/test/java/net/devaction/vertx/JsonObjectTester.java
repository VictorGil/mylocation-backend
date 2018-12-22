package net.devaction.vertx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

/**
 * @author Víctor Gil
 * since June 2018 
 * 
 */
public class JsonObjectTester{
    private static final Logger log = LoggerFactory.getLogger(JsonObjectTester.class);
    
    public static void main(String[] args){
        
        SomeMoney someMoneyOriginal = new SomeMoney(1L, "EUR");
        log.info("SomeMoney original object: " + someMoneyOriginal);
        
        JsonObject jsonObject = JsonObject.mapFrom(someMoneyOriginal);
        log.info("SomeMoney JSON object: " + jsonObject);
        
        SomeMoney someMoneyRecreated = jsonObject.mapTo(SomeMoney.class);
        log.info("SomeMoney object recreated from JSON: " + someMoneyRecreated);
    }
}
