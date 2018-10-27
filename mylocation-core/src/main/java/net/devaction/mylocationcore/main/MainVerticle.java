package net.devaction.mylocationcore.main;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocationcore.serverforandroid.LocationDataServerVerticle;
import net.devaction.mylocationcore.serverforwebbrowser.WebServerVerticle;
import net.devaction.mylocationcore.util.DecryptedValuesProvider;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class MainVerticle extends AbstractVerticle{
    private static final Logger log = LogManager.getLogger(MainVerticle.class);

    private static JsonObject appConfig;
    private DecryptedValuesProvider decryptedValuesProvider;
    
    @Override
    public void start(){
        log.info("Starting " + MainVerticle.class.getSimpleName());        
        
        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.getConfig(asyncResult -> {
            if (asyncResult.failed()) {
                log.fatal("Failed to retrieve configuration: " + asyncResult.cause(), asyncResult.cause());
                log.info("Terminating application");
                System.exit(1);
            } else{
                appConfig = asyncResult.result();
                log.info("Retrieved configuration: " + appConfig);
                
                //this is for the sun.misc.SignalHandler.handle method to be able to shutdown Vert.x
                MyLocationCoreMain.setVertx(vertx);
                
                deployLocationDataServerVerticle();
                
                String decryptPasswordEnvVarName = appConfig.getJsonObject("app_config").getString("decrypt_password_env_var_name");
                String decryptPassword = System.getenv(decryptPasswordEnvVarName);
                if (decryptPassword == null || decryptPassword.length() == 0) {
                    String errMsg = "The decryption password cannot be null nor empty";
                    log.fatal(errMsg);
                    log.info("Terminating application");
                    System.exit(1);
                }
                decryptedValuesProvider = new DecryptedValuesProvider(decryptPassword);
                deployWebServerVerticle();
            }
          });     
    }
    
    public void deployLocationDataServerVerticle(){
        log.info("Going to deploy " + LocationDataServerVerticle.class.getSimpleName());
        vertx.deployVerticle(new LocationDataServerVerticle(), asyncResult -> {
            if (asyncResult.succeeded()){
                log.info("Successfully deployed " +  
                        LocationDataServerVerticle.class.getSimpleName() + ". Result: " + asyncResult.result());
            } else{
                log.error("Error when trying to deploy " + LocationDataServerVerticle.class.getSimpleName());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });    
    }    

    public void deployWebServerVerticle(){
        log.info("Going to deploy " + WebServerVerticle.class.getSimpleName());
        
        String keyStorePasswordEncrypted = appConfig.getJsonObject("app_config").getString("keystore_password_encrypted");
        String keyStorePassword = decryptedValuesProvider.decrypt(keyStorePasswordEncrypted);
        
        vertx.deployVerticle(new WebServerVerticle(keyStorePassword), asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("Successfully deployed " +  
                        WebServerVerticle.class.getSimpleName() + ". Result: " + asyncResult.result());
            } else {
                log.error("Error when trying to deploy " + WebServerVerticle.class.getSimpleName());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });    
    } 

    @Override
    public void stop(){
        log.info("Verticle has been stopped");
    }

    public static JsonObject getAppConfig() {
        return appConfig;
    }
}

