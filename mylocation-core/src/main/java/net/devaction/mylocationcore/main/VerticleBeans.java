package net.devaction.mylocationcore.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devaction.mylocationcore.serverforandroid.LocationDataServerVerticle;
import net.devaction.mylocationcore.serverforwebbrowser.WebServerVerticle;

/**
 * @author Víctor Gil
 * since October 2018
 */
public class VerticleBeans{
    private static final Logger log = LogManager.getLogger(VerticleBeans.class);

    private LocationDataServerVerticle locationDataServerVerticle;
    private WebServerVerticle webServerVerticle;

    public void setLocationDataServerVerticle(LocationDataServerVerticle locationDataServerVerticle) {
        this.locationDataServerVerticle = locationDataServerVerticle;
    }
    
    public LocationDataServerVerticle getLocationDataServerVerticle() {
        return locationDataServerVerticle;
    }

    public WebServerVerticle getWebServerVerticle() {
        return webServerVerticle;
    }
    
    public void setWebServerVerticle(WebServerVerticle webServerVerticle) {
        this.webServerVerticle = webServerVerticle;
    }    
}

