package net.devaction.mylocation.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Víctor Gil
 * 
 * since June 2018
 */

@JsonIgnoreProperties({"timeCheckedString", "timeMeasuredString"})
public class LocationData{
    public static final String NA = "N/A";
    
    //in degrees ("[+-]DDD.DDDDD")
    @JsonProperty  
    private String latitude;
    
    //in degrees ("[+-]DDD.DDDDD")
    @JsonProperty  
    private String longitude;
    
    //accuracy in meters
    @JsonProperty  
    private String horizontalAccuracy;    
    
    @JsonProperty  
    private String altitude;
    
    @JsonProperty  
    private String verticalAccuracy;
    
    //when Android was asked for the last known location
    @JsonProperty  
    private long timeChecked;
    
    //when the location was measured by Android
    //UTC time in seconds since January 1, 1970.
    @JsonProperty  
    private long timeMeasured;

    @Override
    public String toString() {
        return "LocationData [latitude=" + latitude + ", longitude=" + longitude + ", horizontalAccuracy="
                + horizontalAccuracy + ", altitude=" + altitude + ", verticalAccuracy=" + verticalAccuracy
                + ", timeChecked=" + getTimeCheckedString() + " (" + timeChecked + ")" + ", timeMeasured=" + 
                getTimeMeasuredString() + " (" + timeMeasured + ")]";
    }

    //getters and setters
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHorizontalAccuracy() {
        return horizontalAccuracy;
    }

    public void setHorizontalAccuracy(String horizontalAccuracy) {
        this.horizontalAccuracy = horizontalAccuracy;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getVerticalAccuracy() {
        return verticalAccuracy;
    }

    public void setVerticalAccuracy(String verticalAccuracy) {
        this.verticalAccuracy = verticalAccuracy;
    }

    public long getTimeChecked() {
        return timeChecked;
    }
    
    public String getTimeCheckedString() {
        return DateUtil.getDateString(timeChecked);
    }  
    
    public void setTimeChecked(long timeChecked) {
        this.timeChecked = timeChecked;
    }

    public long getTimeMeasured() {
        return timeMeasured;
    }

    public void setTimeMeasured(long timeMeasured) {
        this.timeMeasured = timeMeasured;
    }    
    
    public String getTimeMeasuredString() {
        return DateUtil.getDateString(timeMeasured);
    }
}

