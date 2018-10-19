package net.devaction.vertx.simpleverticle02;

import org.apache.logging.log4j.Logger;

import io.vertx.core.Launcher;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class VerticlesTester02 {
    private static final Logger log = LogManager.getLogger(VerticlesTester02.class);
    private static final String run = "run";
    
    public static void main(String[] args){
        log.info("Starting application");
        Launcher.executeCommand(run, Verticle01.class.getName());  
        log.info("Main method has finished");
    }  
}

