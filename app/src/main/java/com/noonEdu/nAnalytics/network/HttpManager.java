package com.noonEdu.nAnalytics.network;

import com.noonEdu.nAnalytics.commons.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Vaibhav Bhandula on 18/11/16.
 */
public class HttpManager {

    public static final int HTTP_OK = 200;
    public static final int HTTP_ERROR = 503;

    private static OkHttpClient client;

    public static void initialize() {
        OkHttpClient.Builder normalBuilder = new OkHttpClient.Builder();
        try {
            normalBuilder.connectTimeout(2, TimeUnit.MINUTES);
            normalBuilder.readTimeout(2, TimeUnit.MINUTES);
            normalBuilder.writeTimeout(2, TimeUnit.MINUTES);
        } catch (Throwable t) {
            LogUtils.printStackTrace(t);
        }
        client = normalBuilder.build();
    }

    static Response simpleExecute(Request request) throws IOException {
        return client.newCall(request).execute();
    }
}
