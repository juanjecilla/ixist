package com.stnieva.android.apps.ixist.socialnetwork.twitter;

import android.content.Context;

import com.stnieva.android.apps.ixist.MainActivity;
import com.stnieva.android.apps.ixist.socialnetwork.AbstractSocialNetwork;
import com.stnieva.android.apps.ixist.socialnetwork.SocialNetworkException;
import com.stnieva.android.apps.ixist.socialnetwork.Type;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

import static com.stnieva.android.apps.ixist.socialnetwork.twitter.Constants.TWITTER_KEY;
import static com.stnieva.android.apps.ixist.socialnetwork.twitter.Constants.TWITTER_SECRET;

/**
 * Created by stnieva on 3/4/15.
 */
public class Twitter extends AbstractSocialNetwork {

    public Twitter(Context context) {
        super(context);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(context, new com.twitter.sdk.android.Twitter(authConfig));
    }

    @Override
    public boolean hasError(int keyCode, String regularExpression) {
        return Pattern.matches("/^[[:alnum:]\\x5F]+$/", Integer.toHexString(keyCode));
    }

    @Override
    public void updateUsername(final String username) {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession appSession = appSessionResult.data;

                new ApiClient(appSession).getService().show(username, new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        getOnPublicProfileListener().onPublicProfile(new com.stnieva.android.apps.ixist.socialnetwork.User(userResult.data.idStr, userResult.data.screenName, userResult.data.name, Type.TWITTER));
                    }

                    @Override
                    public void failure(TwitterException e) {
                        getOnErrorListener().onError(new SocialNetworkException("Had an unknown error in request"));
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
                getOnErrorListener().onError(new SocialNetworkException("Had an unknown error in trying to login as a guest"));
            }
        });
    }
}
