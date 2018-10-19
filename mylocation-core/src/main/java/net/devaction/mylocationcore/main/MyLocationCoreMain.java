package net.devaction.mylocationcore.main;

import org.apache.logging.log4j.Logger;

import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import sun.misc.Signal;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil 
 * since June
 */
@SuppressWarnings("restriction")
public class MyLocationCoreMain implements sun.misc.SignalHandler{
    private static final Logger log = LogManager.getLogger(MyLocationCoreMain.class);
    public  static Vertx vertx;
    private static final String WINCH_SIGNAL = "WINCH";
    private static boolean isVertxClosed = false;  
    
    public static void main(String[] args){
        log.info("Starting application");        
        Launcher.executeCommand("run", MainVerticle.class.getName());   

        new MyLocationCoreMain().run();        
    }

    private MyLocationCoreMain(){}
    
    private void run(){
        registerThisAsOsSignalHandler();
    }
    
    //the MainVerticle will call this method for this class to be able to close Vert.x gracefully
    public static void setVertx(Vertx vertxArg){
       vertx = vertxArg;
    }

    public static Vertx getVertx(){
        return vertx;    
    }
    
    private void registerThisAsOsSignalHandler(){
        log.info("Going to register this object to handle the " + WINCH_SIGNAL + " signal");
        try{
            sun.misc.Signal.handle( new sun.misc.Signal(WINCH_SIGNAL), this);
        } catch(IllegalArgumentException ex){
            // Most likely this is a signal that's not supported on this
            // platform or with the JVM as it is currently configured
            log.error(ex, ex);
        } catch(Throwable th){
            // We may have a serious problem, including missing classes
            // or changed APIs
            log.error("I guess the signal is unsupported: " + WINCH_SIGNAL, th);
        }        
    }
    
    @Override
    public void handle(Signal signal) {
        log.info("Signal " + signal.getName() + " has been captured.");
        closeVertxAndExit();        
    }
    
    private static void closeVertxAndExit(){
        log.info("Going to close Vert.x");
        vertx.close(closeHandler -> {
            log.info("Vert.x has been closed");
            isVertxClosed = true;
        });
        
        waitUntilVertxIsClosedAndExit();
    }
    
    private static void waitUntilVertxIsClosedAndExit(){
        log.info("Waiting for Vert.x to close");
        int i = 0;
        while (!isVertxClosed || i < 50){
            try{
                Thread.sleep(100);
                i++;
            } catch(InterruptedException ex){
                log.error(ex, ex);
            }
        }        
        if (isVertxClosed){
            log.info("Verx is closed. Going to exit.");
            System.exit(0);
        } else{ 
            log.error("Vert.x did not det closed after waiting 5 seconds. Going to exit anyway");
            System.exit(1);
        }        
    }
}

