package com.stnieva.android.apps.ixist.model.facebook;

import android.content.Context;

import com.stnieva.android.apps.ixist.model.AbstractSocialNetwork;
import com.stnieva.android.apps.ixist.model.CallbackUser;
import com.stnieva.android.apps.ixist.model.SocialNetworkException;
import com.stnieva.android.apps.ixist.model.User;

import java.util.regex.Pattern;

/**
 * Created by stnieva on 1/4/15.
 */
public class Facebook extends AbstractSocialNetwork {

    public Facebook(Context context, CallbackUser callback) {
        super(context, callback);
    }

    @Override
    public boolean hasError(int keyCode) {
        return Pattern.matches("/^[[:alnum:]\\x2E]+$/", Integer.toHexString(keyCode));
    }

    @Override
    public void updateUsername(String username) {
        FacebookClient client = new FacebookClient(new CallbackUser() {
            @Override
            public void onSuccess(User user) {
                getCallback().onSuccess(user);
            }

            @Override
            public void onFailure(SocialNetworkException e) {
                getCallback().onFailure(e);
            }
        });
        client.execute(username);
    }
}
