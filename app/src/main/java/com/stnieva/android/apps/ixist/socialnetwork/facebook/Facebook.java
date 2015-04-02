package com.stnieva.android.apps.ixist.socialnetwork.facebook;

import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.stnieva.android.apps.ixist.socialnetwork.AbstractSocialNetwork;
import com.stnieva.android.apps.ixist.socialnetwork.SocialNetworkException;
import com.stnieva.android.apps.ixist.socialnetwork.Type;
import com.stnieva.android.apps.ixist.socialnetwork.User;

import java.util.Arrays;
import java.util.regex.Pattern;

import static com.facebook.AccessTokenSource.FACEBOOK_APPLICATION_NATIVE;
import static com.stnieva.android.apps.ixist.socialnetwork.facebook.Constants.ACCESS_TOKEN;

/**
 * Created by stnieva on 1/4/15.
 */
public class Facebook extends AbstractSocialNetwork implements UserId.OnFinishListener {

    public Facebook(Context context) {
        super(context);
    }

    @Override
    public boolean hasError(int keyCode, String regularExpression) {
        return Pattern.matches("/^[[:alnum:]\\x2E]+$/", Integer.toHexString(keyCode));
    }

    @Override
    public void updateUsername(String username) {
        UserId id = new UserId(this);
        id.execute(username);
    }

    @Override
    public void onFinish(final String userId) {
        if (userId == null) {
            getOnErrorListener().onError(new SocialNetworkException("Couldn't get the user identifier"));
            return;
        }

        AccessToken accessToken = AccessToken.createFromExistingAccessToken(ACCESS_TOKEN, null, null, FACEBOOK_APPLICATION_NATIVE, Arrays.asList(new String[]{"public_profile"}));

        Session.openActiveSessionWithAccessToken(getContext(), accessToken, new Session.StatusCallback() {

            @Override
            public void call(Session session, SessionState sessionState, Exception e) {
                new Request(session, String.format("/%s", userId), null, HttpMethod.GET, new Request.Callback() {

                    @Override
                    public void onCompleted(Response response) {
                        GraphObject graphObject = response.getGraphObject();

                        if (graphObject != null) {
                            String username = (String) graphObject.getProperty("username");

                            String name = (String) graphObject.getProperty("name");

                            getOnPublicProfileListener().onPublicProfile(new User(userId, username, name, Type.FACEBOOK));
                        } else {
                            getOnErrorListener().onError(new SocialNetworkException("Had an unknown error in request"));
                        }
                    }
                }).executeAsync();
            }
        });
    }
}
