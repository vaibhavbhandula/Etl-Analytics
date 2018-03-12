package com.noonEdu.nAnalytics.network;

import com.noonEdu.nAnalytics.exception.UrlEmptyException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public class ApiClient {

    private static String baseDebugUrl = "", baseUrl = "";
    private static boolean isTestUrl = false;

    private static Retrofit debugRetrofit = null, retrofit = null;

    public static void initialize(String baseDebugUrl, String baseUrl, boolean isTestUrl) {
        ApiClient.baseDebugUrl = baseDebugUrl;
        ApiClient.baseUrl = baseUrl;
        ApiClient.isTestUrl = isTestUrl;
    }

    private static Retrofit getDebugRetrofit() throws UrlEmptyException {
        if (debugRetrofit == null) {
            if (baseDebugUrl == null || baseDebugUrl.isEmpty()) {
                throw new UrlEmptyException("Url Empty!");
            }
            debugRetrofit = new Retrofit.Builder()
                    .baseUrl(baseDebugUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return debugRetrofit;
    }

    public static Retrofit getClient() throws UrlEmptyException {
        if (retrofit == null) {
            if (baseUrl == null || baseUrl.isEmpty()) {
                throw new UrlEmptyException("Url Empty!");
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if (isTestUrl) {
            return getDebugRetrofit();
        } else {
            return retrofit;
        }
    }

    public static boolean isResponseOk(int code) {
        return (code >= 200 && code <= 210);
    }
}
