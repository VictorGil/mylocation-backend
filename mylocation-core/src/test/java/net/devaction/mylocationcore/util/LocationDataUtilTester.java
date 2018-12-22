package net.devaction.mylocationcore.util;

import net.devaction.mylocation.api.data.LocationData;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class LocationDataUtilTester{
    private static final Logger log = LoggerFactory.getLogger(LocationDataUtilTester.class);
    
    public static void main(String[] args){
        LocationData locationData = constructTestLocationData();
        log.info("LocationData object: " + locationData);
        String locationDataString = LocationDataUtil.convertToJsonString(locationData);
        log.info("LocationData String: " + locationDataString);
    }
    
    public static LocationData constructTestLocationData(){
        LocationData locationData = new LocationData();
        
        locationData.setLatitude("1");
        locationData.setLongitude("3.1");
        locationData.setHorizontalAccuracy("2");
        
        locationData.setAltitude("5");
        locationData.setVerticalAccuracy("6");
        
        long currentUnixTimeInSeconds = new Date().getTime() / 1000;
        locationData.setTimeChecked(currentUnixTimeInSeconds);        
        locationData.setTimeMeasured(currentUnixTimeInSeconds - 1);
        
        return locationData;
    } 
}


