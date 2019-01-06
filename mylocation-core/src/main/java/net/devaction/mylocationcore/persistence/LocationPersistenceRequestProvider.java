package net.devaction.mylocationcore.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devaction.mylocation.api.data.LocationData;
import net.devaction.mylocation.locationpersistenceapi.protobuf.LocationPersistenceRequest;
import net.devaction.mylocation.locationpersistenceapi.util.DateUtil;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class LocationPersistenceRequestProvider{
    private static final Logger log = LoggerFactory.getLogger(LocationPersistenceRequestProvider.class);
    
    public LocationPersistenceRequest provide(LocationData locationData){
        LocationPersistenceRequest.Builder requestBuilder = LocationPersistenceRequest.newBuilder();
        
        if (locationData.getLatitude() != null)
            requestBuilder.setLatitude(locationData.getLatitude());
        
        if (locationData.getLongitude() != null)
            requestBuilder.setLongitude(locationData.getLongitude());
        
        if (locationData.getHorizontalAccuracy() != null)
            requestBuilder.setHorizontalAccuracy(locationData.getHorizontalAccuracy());
        
        if (locationData.getAltitude() != null)
            requestBuilder.setAltitude(locationData.getAltitude());
        
        if (locationData.getVerticalAccuracy() != null)
            requestBuilder.setVerticalAccuracy(locationData.getVerticalAccuracy());
        
        if (locationData.getTimeChecked() > 0)
            requestBuilder.setTimeChecked(locationData.getTimeChecked());
        
        if (locationData.getTimeMeasured() > 0)
            requestBuilder.setTimeMeasured(locationData.getTimeMeasured());
        
        requestBuilder.setTimestamp(DateUtil.getCurrentUnixTime());
        
        return requestBuilder.build();
    }
}

