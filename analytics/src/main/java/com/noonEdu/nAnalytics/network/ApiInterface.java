package com.noonEdu.nAnalytics.network;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public interface ApiInterface {

    @POST()
    Call<Void> sendPostEvent(@Url String url, @Body RequestBody requestBody);

    @PUT()
    Call<Void> sendPutEvent(@Url String url, @Body RequestBody requestBody);
}
