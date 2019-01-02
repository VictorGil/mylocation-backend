package net.devaction.mylocationcore.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Víctor Gil
 */
public class AppConfig{
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @JsonProperty("location_data_http_port")
    private int locationDataHttpPort;
    
    @JsonProperty("location_data_end_point")
    private String locationDataEndPoint;
    
    @JsonProperty("web_server_http_port")
    private int webServerHttpPort;
    
    //this file must be present in the classpath
    @JsonProperty("web_server_keystore_file")
    private String webServerKeystoreFile;
    
    @JsonProperty("decrypt_password_env_var_name")
    private String decryptPasswordEnvVarName;
    
    @JsonProperty("keystore_password_encrypted")
    private String keystorePasswordEncrypted;
    
    @JsonProperty("event_bus_multicast_address")
    private String eventBusMulticastAddress;

    @JsonProperty("event_bus_last_known_location_address")
    private String eventBusLastKnownLocationAddress;

    @JsonProperty("event_bus_location_persist_address")
    private String eventBusLocationPersistAddress;    
    
    @Override
    public String toString() {
        return "application_configuration [locationDataHttpPort=" + locationDataHttpPort + ", locationDataEndPoint="
                + locationDataEndPoint + ", webServerHttpPort=" + webServerHttpPort + ", webServerKeystoreFile="
                + webServerKeystoreFile + ", decryptPasswordEnvVarName=" + decryptPasswordEnvVarName
                + ", keystorePasswordEncrypted=" + keystorePasswordEncrypted + ", eventBusMulticastAddress="
                + eventBusMulticastAddress + ", eventBusLastKnownLocationAddress=" + eventBusLastKnownLocationAddress
                + ", eventBusLocationPersistAddress=" + eventBusLocationPersistAddress + "]";
    }

    public int getLocationDataHttpPort() {
        return locationDataHttpPort;
    }

    public void setLocationDataHttpPort(int locationDataHttpPort) {
        this.locationDataHttpPort = locationDataHttpPort;
    }

    public String getLocationDataEndPoint() {
        return locationDataEndPoint;
    }

    public void setLocationDataEndPoint(String locationDataEndPoint) {
        this.locationDataEndPoint = locationDataEndPoint;
    }

    public int getWebServerHttpPort() {
        return webServerHttpPort;
    }

    public void setWebServerHttpPort(int webServerHttpPort) {
        this.webServerHttpPort = webServerHttpPort;
    }

    public String getWebServerKeystoreFile() {
        return webServerKeystoreFile;
    }

    public void setWebServerKeystoreFile(String webServerKeystoreFile) {
        this.webServerKeystoreFile = webServerKeystoreFile;
    }

    public String getDecryptPasswordEnvVarName() {
        return decryptPasswordEnvVarName;
    }

    public void setDecryptPasswordEnvVarName(String decryptPasswordEnvVarName) {
        this.decryptPasswordEnvVarName = decryptPasswordEnvVarName;
    }

    public String getKeystorePasswordEncrypted() {
        return keystorePasswordEncrypted;
    }

    public void setKeystorePasswordEncrypted(String keystorePasswordEncrypted) {
        this.keystorePasswordEncrypted = keystorePasswordEncrypted;
    }

    public String getEventBusMulticastAddress() {
        return eventBusMulticastAddress;
    }

    public void setEventBusMulticastAddress(String eventBusMulticastAddress) {
        this.eventBusMulticastAddress = eventBusMulticastAddress;
    }

    public String getEventBusLastKnownLocationAddress() {
        return eventBusLastKnownLocationAddress;
    }

    public void setEventBusLastKnownLocationAddress(String eventBusLastKnownLocationAddress) {
        this.eventBusLastKnownLocationAddress = eventBusLastKnownLocationAddress;
    }

    public String getEventBusLocationPersistAddress() {
        return eventBusLocationPersistAddress;
    }

    public void setEventBusLocationPersistAddress(String eventBusLocationPersistAddress) {
        this.eventBusLocationPersistAddress = eventBusLocationPersistAddress;
    }   
}

