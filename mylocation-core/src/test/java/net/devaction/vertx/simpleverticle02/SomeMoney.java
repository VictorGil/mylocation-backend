package net.devaction.vertx.simpleverticle02;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;
import java.io.Serializable;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class SomeMoney implements Serializable{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LogManager.getLogger(SomeMoney.class);
    
    @JsonProperty   
    private long amount;
    @JsonProperty
    private String currency;

    public SomeMoney(){}
        
    public SomeMoney(long amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public JsonObject toJsonObject(){
        return JsonObject.mapFrom(this);        
    }
    
    public static SomeMoney getNewInstance(JsonObject jsonObject){
        return jsonObject.mapTo(SomeMoney.class);
    }
    
    @Override
    public String toString() {
        return "SomeMoney [amount=" + amount + ", currency=" + currency + "]";
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }   
}

