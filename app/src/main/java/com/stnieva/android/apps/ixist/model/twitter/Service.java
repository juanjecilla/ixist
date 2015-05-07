package com.stnieva.android.apps.ixist.model.twitter;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.User;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by stnieva on 18/4/15.
 */
public interface Service {

    @GET("/1.1/users/show.json")
    void show(@Query("screen_name") String screenName, Callback<User> callback);
}
