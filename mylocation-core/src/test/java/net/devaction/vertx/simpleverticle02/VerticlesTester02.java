package net.devaction.vertx.simpleverticle02;

import io.vertx.core.Launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class VerticlesTester02 {
    private static final Logger log = LoggerFactory.getLogger(VerticlesTester02.class);
    private static final String run = "run";
    
    public static void main(String[] args){
        log.info("Starting application");
        Launcher.executeCommand(run, Verticle01.class.getName());  
        log.info("Main method has finished");
    }  
}

