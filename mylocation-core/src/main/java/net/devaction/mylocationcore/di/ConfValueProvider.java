package net.devaction.mylocationcore.di;

/**
 * @author Víctor Gil
 * since October 2018 
 * 
 */
public interface ConfValueProvider{

    public String getString(String key);
    
    public Integer getInteger(String key);
    
    public Double getDouble(String key);
    
    public Long getLong(String key);    
}
