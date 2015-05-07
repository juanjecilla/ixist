package com.stnieva.android.apps.ixist.model;

import java.util.List;

/**
 * Created by stnieva on 25/4/15.
 */
public interface CallbackUsers {

    void success(List<User> users);

    void failure(SocialNetworkException e);
}
