package com.stnieva.android.apps.ixist.model.twitter;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;

/**
 * Created by stnieva on 3/4/15.
 */
public class TwitterClient extends TwitterApiClient {

    public TwitterClient(Session session) {
        super(session);
    }

    public Service getService() {
        return getService(Service.class);
    }
}
