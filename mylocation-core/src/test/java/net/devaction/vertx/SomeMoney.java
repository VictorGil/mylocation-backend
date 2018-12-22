package net.devaction.vertx;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018  
 */
public class SomeMoney implements Serializable{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(SomeMoney.class);
    
    @JsonProperty   
    private long amount;
    @JsonProperty
    private String currency;

    public SomeMoney(){}
    
    public SomeMoney(long amount, String currency) {
        this.amount = amount;
        this.currency = currency;
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

