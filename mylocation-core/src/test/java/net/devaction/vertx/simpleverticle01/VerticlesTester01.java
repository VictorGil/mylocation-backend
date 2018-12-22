package net.devaction.vertx.simpleverticle01;

import io.vertx.core.Launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018  
 */
public class VerticlesTester01 {
    private static final Logger log = LoggerFactory.getLogger(VerticlesTester01.class);
    private static final String run = "run";
    
    public static void main(String[] args){
        log.info("Starting application");
        Launcher.executeCommand(run, Verticle01.class.getName());        
    }  
}

