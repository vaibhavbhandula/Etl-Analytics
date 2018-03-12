package com.noonEdu.nAnalytics.network;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public interface ApiInterface {

    @POST()
    Call<Void> sendEvent();
}
