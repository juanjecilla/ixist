package com.stnieva.android.apps.ixist.model.twitter;

import android.content.Context;
import android.util.Log;

import com.stnieva.android.apps.ixist.model.AbstractSocialNetwork;
import com.stnieva.android.apps.ixist.model.CallbackUser;
import com.stnieva.android.apps.ixist.model.SocialNetworkException;
import com.stnieva.android.apps.ixist.model.Type;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

import static com.stnieva.android.apps.ixist.model.twitter.Constants.TWITTER_KEY;
import static com.stnieva.android.apps.ixist.model.twitter.Constants.TWITTER_SECRET;

/**
 * Created by stnieva on 3/4/15.
 */
public class Twitter extends AbstractSocialNetwork {

    public Twitter(Context context, CallbackUser callback) {
        super(context, callback);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(context, new com.twitter.sdk.android.Twitter(authConfig));
    }

    @Override
    public boolean hasError(int keyCode) {
        return Pattern.matches("/^[[:alnum:]\\x5F]+$/", Integer.toHexString(keyCode));
    }

    @Override
    public void updateUsername(final String username) {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession appSession = appSessionResult.data;

                new TwitterClient(appSession).getService().show(username, new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        com.stnieva.android.apps.ixist.model.User user = new com.stnieva.android.apps.ixist.model.User(
                                Type.TWITTER,
                                true,
                                userResult.data.idStr,
                                userResult.data.screenName,
                                userResult.data.name
                        );
                        getCallback().onSuccess(user);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        getCallback().onFailure(new SocialNetworkException("The username you requested do not exist"));

                        com.stnieva.android.apps.ixist.model.User user = new com.stnieva.android.apps.ixist.model.User(
                                Type.TWITTER,
                                false,
                                null,
                                null,
                                null
                        );
                        getCallback().onSuccess(user);
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
                Log.d("Twitter", e.getMessage());
            }
        });
    }
}
