package net.devaction.mylocationcore.serverforandroid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devaction.httptest.HttpSender;
import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocationcore.util.LocationDataUtil;
import net.devaction.mylocationcore.util.LocationDataUtilTester;

/**
 * @author Víctor Gil
 */
public class LocationDataServerVerticleTester{
    private static final Logger log = LogManager.getLogger(LocationDataServerVerticleTester.class);

    public static void main(String[] args){
        new LocationDataServerVerticleTester().run1();
    }
    
    private void run1(){
        LocationData data = LocationDataUtilTester.constructTestLocationData();
        String dataString = LocationDataUtil.convertToJsonString(data);
        
        String url = "http://localhost:8091/api/locationdata";
        
        try{
            Thread.sleep(5000);
        } catch(InterruptedException ex){
            log.error(ex, ex);
        }
        HttpSender.send(url, dataString);        
    }
}

