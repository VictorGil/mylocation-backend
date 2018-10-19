package net.devaction.mylocation.api.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** 
 * @author Víctor Gil
 * */
public class DateUtil{
    
    public static final String DATE_FORMAT = "EEEE dd-MMM-yyyy HH:mm:ssZ";
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT,
            new Locale("en"));
        
    public static Date getDate(long unixTime){
        //we received the number of seconds (not milliseconds) from 1/1/1970 as argument
        return new Date(unixTime * 1000L);
    }

    public static Date getDateFromJavaTime(long javaTime){
        //we received the number of milliseconds (not seconds) from 1/1/1970 as argument
        return new Date(javaTime);
    }
    
    //it returns something like: "Tuesday 05-Jun-2018 19:57:22+0200";
    public static String getDateString(long unixTime){
        return FORMATTER.format(getDate(unixTime));        
    }

    //it returns something like: "Tuesday 05-Jun-2018 19:57:22+0200";
    public static String getDateStringFromJavaTime(long javaTime){
        return FORMATTER.format(getDateFromJavaTime(javaTime));        
    }
    
    public static String getDateString(Date date){
        return getDateStringFromJavaTime(date.getTime());        
    }
    
    public static long getUnixTime(Date date){
        return date.getTime()/1000L;
    }
    
    public static long getUnixTimeFromJavaTime(long javaTime){
        return javaTime/1000L;
    }
    
    public static long getJavaTimeFromUnixTime(long unixTime){
        return unixTime * 1000L;
    }
    
    public static long getCurrentUnixTime(){
        return getUnixTime(new Date());
    }
    
    //Date string example: "Tuesday 05-Jun-2018 19:57:22+0200"
    public static long getUnixTime(String dateString){
        return getUnixTime(getDate(dateString));
    }
    
    //Date string example: "Tuesday 05-Jun-2018 19:57:22+0200"
    public static Date getDate(String dateString){
        Date date = null;
        try{
            date = FORMATTER.parse(dateString);
        }catch(ParseException ex){
            String errMessage = "Unable to parse date string: " + dateString + ", correct format is: " 
                    + DATE_FORMAT + ". " + ex.getMessage();
            throw new RuntimeException(errMessage, ex);
        }    
        return date;
    }
    
    //Date string example: "Tuesday 05-Jun-2018 19:57:22+0200"
    public static long getJavaTimeFromDateString(String dateString){
        return getDate(dateString).getTime();
    }
}

