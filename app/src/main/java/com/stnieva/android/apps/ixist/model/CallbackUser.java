package com.stnieva.android.apps.ixist.model;

/**
 * Created by stnieva on 18/4/15.
 */
public interface CallbackUser {

    void onSuccess(User user);

    void onFailure(SocialNetworkException e);
}
