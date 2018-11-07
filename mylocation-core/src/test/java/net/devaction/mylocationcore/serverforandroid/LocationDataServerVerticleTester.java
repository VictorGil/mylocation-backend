package net.devaction.mylocationcore.serverforandroid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devaction.httptest.HttpSender;
import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocationcore.test.config.AppConfig;
import net.devaction.mylocationcore.test.config.AppConfigProvider;
import net.devaction.mylocationcore.util.LocationDataUtil;
import net.devaction.mylocationcore.util.LocationDataUtilTester;

/**
 * @author Víctor Gil
 * since October 2018
 */
public class LocationDataServerVerticleTester{
    private static final Logger log = LogManager.getLogger(LocationDataServerVerticleTester.class);

    public static void main(String[] args){
        new LocationDataServerVerticleTester().run1();
    }
    
    private void run1(){
        final LocationData data = LocationDataUtilTester.constructTestLocationData();
        final String dataString = LocationDataUtil.convertToJsonString(data);
        
        final AppConfig appConfig = AppConfigProvider.provide();
        
        final String url = "https://localhost:" + appConfig.getLocationDataHttpPort() + 
                appConfig.getLocationDataEndPoint();
        log.info("URL to send the fake/test location data: " + url);        
       
        HttpSender.send(url, dataString);        
    }
}

