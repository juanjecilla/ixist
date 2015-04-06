package com.stnieva.android.apps.ixist.socialnetwork;

import android.content.Context;

import com.stnieva.android.apps.ixist.socialnetwork.facebook.Facebook;
import com.stnieva.android.apps.ixist.socialnetwork.twitter.Twitter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stnieva on 6/4/15.
 */
public class SocialNetworks implements IHasError, IUpdateUsername, AbstractSocialNetwork.OnPublicProfileListener, AbstractSocialNetwork.OnErrorListener {

    private Context context;
    private Callback callback;
    private List<AbstractSocialNetwork> networks = new LinkedList<>();
    private List<User> users = new LinkedList<>();

    public SocialNetworks(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;

        Facebook facebook = new Facebook(context);
        facebook.setOnErrorListener(this);
        facebook.setOnPublicProfileListener(this);
        networks.add(facebook);

        Twitter twitter = new Twitter(context);
        twitter.setOnErrorListener(this);
        twitter.setOnPublicProfileListener(this);
        networks.add(twitter);
    }

    @Override
    public boolean hasError(int keyCode) {
        boolean value = false;

        Iterator<AbstractSocialNetwork> iterator = networks.iterator();

        while (iterator.hasNext() && value == false) {
            value = iterator.next().hasError(keyCode);
        }

        return value;
    }

    @Override
    public void updateUsername(String username) {
        Iterator<AbstractSocialNetwork> iterator = networks.iterator();

        while (iterator.hasNext()) {
            iterator.next().updateUsername(username);
        }
    }

    @Override
    public void onError(SocialNetworkException exception) {
        callback.failure(exception);
    }

    @Override
    public void onPublicProfile(User user) {
        users.add(user);

        if (users.size() == networks.size()) {
            callback.success(users);
        }
    }

    public interface Callback {
        void success(List<User> users);

        void failure(SocialNetworkException e);
    }
}
