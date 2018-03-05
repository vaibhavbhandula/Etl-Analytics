package com.noonEdu.nAnalytics.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public class ApiClient {

    private static final boolean IS_TEST_URL = false;

    private static final String BASE_DEBUG_URL = "https://testapi.non.sa/";
    private static final String BASE_URL = "https://api.non.sa/";

    private static Retrofit debugRetrofit = null, retrofit = null;

    private static Retrofit getDebugRetrofit() {
        if (debugRetrofit == null) {
            debugRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_DEBUG_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return debugRetrofit;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if (IS_TEST_URL) {
            return getDebugRetrofit();
        } else {
            return retrofit;
        }
    }

    public static boolean isResponseOk(int code) {
        return (code >= 200 && code <= 210);
    }
}
