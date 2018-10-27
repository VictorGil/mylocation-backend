package net.devaction.mylocationcore.main;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocationcore.di.ConfValueProviderImpl;
import net.devaction.mylocationcore.di.VertxProviderImpl;
import net.devaction.mylocationcore.serverforandroid.LocationDataServerVerticle;
import net.devaction.mylocationcore.serverforwebbrowser.WebServerVerticle;
import net.devaction.mylocationcore.util.DecryptedValueProvider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class MainVerticle extends AbstractVerticle{
    private static final Logger log = LogManager.getLogger(MainVerticle.class);

    //private static JsonObject appConfig;
    //private DecryptedValueProvider decryptedValuesProvider;
    private JsonObject vertxConfig;
    
    @Override
    public void start(){
        log.info("Starting " + this.getClass().getSimpleName());        

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.getConfig(asyncResult -> {
            if (asyncResult.failed()) {
                log.fatal("Failed to retrieve configuration: " + asyncResult.cause(), asyncResult.cause());
                vertx.close(closeHandler -> {
                    log.info("Vert.x has been closed");
                });
            } else{
                vertxConfig = asyncResult.result();
                log.info("Retrieved configuration: " + vertxConfig);
                
                //This is a workaround, kind of
                ConfValueProviderImpl.setAppConfig(vertxConfig.getJsonObject("app_config"));                
                VertxProviderImpl.setVertx(vertx);
                
                ApplicationContext appContext = new ClassPathXmlApplicationContext("conf/spring/beans.xml");
                VerticleBeans verticleBeans = (VerticleBeans) appContext.getBean("verticleBeans");
                ((ConfigurableApplicationContext) appContext).close();                
                
                //this is for the sun.misc.SignalHandler.handle method to be able to shutdown Vert.x
                MyLocationCoreMain.setVertx(vertx);
                
                deployLocationDataServerVerticle(verticleBeans.getLocationDataServerVerticle());
                
                /*
                String decryptPasswordEnvVarName = appConfig.getJsonObject("app_config").getString("decrypt_password_env_var_name");
                String decryptPassword = System.getenv(decryptPasswordEnvVarName);
                if (decryptPassword == null || decryptPassword.length() == 0) {
                    String errMsg = "The decryption password cannot be null nor empty";
                    log.fatal(errMsg);
                    vertx.close(closeHandler -> {
                        log.info("Vert.x has been closed");
                    });
                    return;
                }*/
                //decryptedValuesProvider = new DecryptedValueProvider(decryptPassword);
                deployWebServerVerticle(verticleBeans.getWebServerVerticle());
            }
        });     
    }
    
    public void deployLocationDataServerVerticle(LocationDataServerVerticle locationDataServerVerticle){
        log.info("Going to deploy " + LocationDataServerVerticle.class.getSimpleName());
        vertx.deployVerticle(locationDataServerVerticle, asyncResult -> {
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

    public void deployWebServerVerticle(WebServerVerticle webServerVerticle){
        log.info("Going to deploy " + WebServerVerticle.class.getSimpleName());
        
        //String keyStorePasswordEncrypted = appConfig.getJsonObject("app_config").getString("keystore_password_encrypted");
        //String keyStorePassword = decryptedValuesProvider.decrypt(keyStorePasswordEncrypted);
        
        vertx.deployVerticle(webServerVerticle, asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("Successfully deployed " +  
                        WebServerVerticle.class.getSimpleName() + ". Result: " + asyncResult.result());
            } else{
                log.error("Error when trying to deploy " + WebServerVerticle.class.getSimpleName());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });    
    } 

    @Override
    public void stop(){
        log.info(this.getClass().getSimpleName() + " verticle has been stopped");
    }
}

