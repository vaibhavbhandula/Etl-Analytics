package com.noonEdu.nAnalytics.network;

import android.support.annotation.Nullable;

import com.noonEdu.nAnalytics.commons.LogUtils;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Vaibhav Bhandula on 18/11/16.
 */
public class PostRequest {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Nullable
    public static Response getPostResponse(String url, String json, String auth) throws Exception {

        RequestBody body = RequestBody.create(JSON, json);

        Request.Builder builder = new Request.Builder().url(url).addHeader("Authorization",
                "Bearer " + auth);

        if (body != null) {
            builder.post(body);
        }

        Request request = builder.build();
        LogUtils.printLog("api call url", request.toString());

        try {
            return HttpManager.simpleExecute(request);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }
}
