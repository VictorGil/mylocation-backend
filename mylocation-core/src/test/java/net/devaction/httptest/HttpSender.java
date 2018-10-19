package net.devaction.httptest;

import org.apache.logging.log4j.Logger;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since June 2018 
 */
public class HttpSender{
    private static final Logger log = LogManager.getLogger(HttpSender.class);

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public static void send(String url, String jsonString){
        OkHttpClient client = new OkHttpClient();

        Request request = buildRequest(url, jsonString);
        Response response = null;
        try{
            response = client.newCall(request).execute();
            log.info("HTTP Response received: " + response.body().string());
        } catch(IOException ex){
            log.info(ex, ex);
        }
        log.info("Response: " + response);
    }

    static Request buildRequest(String url, String jsonString){
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonString);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return request;
    }
}

