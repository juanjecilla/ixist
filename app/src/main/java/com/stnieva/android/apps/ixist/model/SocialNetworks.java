package com.stnieva.android.apps.ixist.model;

import android.content.Context;

import com.stnieva.android.apps.ixist.model.facebook.Facebook;
import com.stnieva.android.apps.ixist.model.twitter.Twitter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stnieva on 6/4/15.
 */
public class SocialNetworks implements IHasError, IUpdateUsername, CallbackUser {

    private Context context;

    private CallbackUsers callback;

    private List<AbstractSocialNetwork> networks = new LinkedList<>();

    private List<User> users = new LinkedList<>();

    public SocialNetworks(Context context, CallbackUsers callback) {
        this.context = context;
        this.callback = callback;

        Facebook facebook = new Facebook(context, this);
        networks.add(facebook);

        Twitter twitter = new Twitter(context, this);
        networks.add(twitter);
    }

    public Context getContext() {
        return context;
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
    public void onSuccess(User user) {
        users.add(user);

        if (users.size() == networks.size()) {
            callback.success(users);
        }
    }

    @Override
    public void onFailure(SocialNetworkException e) {
        callback.failure(e);
    }
}
