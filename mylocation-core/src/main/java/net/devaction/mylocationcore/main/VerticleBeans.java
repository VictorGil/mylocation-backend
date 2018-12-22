package net.devaction.mylocationcore.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devaction.mylocationcore.serverforandroid.LocationDataServerVerticle;
import net.devaction.mylocationcore.serverforwebbrowser.WebServerVerticle;

/**
 * @author Víctor Gil
 * since October 2018
 */
public class VerticleBeans{
    private static final Logger log = LoggerFactory.getLogger(VerticleBeans.class);

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

