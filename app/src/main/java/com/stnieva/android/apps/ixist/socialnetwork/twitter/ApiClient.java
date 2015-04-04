package com.stnieva.android.apps.ixist.socialnetwork.twitter;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.models.User;

import retrofit.http.GET;
import retrofit.http.Query;

interface Service {
    @GET("/1.1/users/show.json")
    void show(@Query("screen_name") String screenName, Callback<User> callback);
}

/**
 * Created by stnieva on 3/4/15.
 */
public class ApiClient extends TwitterApiClient {

    public ApiClient(Session session) {
        super(session);
    }

    public Service getService() {
        return getService(Service.class);
    }
}
