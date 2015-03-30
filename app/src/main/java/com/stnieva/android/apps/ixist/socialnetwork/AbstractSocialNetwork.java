package com.stnieva.android.apps.ixist.socialnetwork;

import android.content.Context;

/**
 * Created by stnieva on 30/3/15.
 */
public abstract class AbstractSocialNetwork {

    public interface OnPublicProfileListener {
        public void onPublicProfile(User user);
    }

    public interface OnErrorListener {
        public void onError(SocialNetworkException exception);
    }

    private Context context;

    private OnPublicProfileListener onPublicProfileListener;

    private OnErrorListener onErrorListener;

    public AbstractSocialNetwork(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public abstract boolean hasError(int keyCode, String regularExpression);

    public abstract void updateUsername(String username);

    protected OnPublicProfileListener getOnPublicProfileListener() {
        return onPublicProfileListener;
    }

    public void setOnPublicProfileListener(OnPublicProfileListener l) {
        this.onPublicProfileListener = l;
    }

    protected OnErrorListener getOnErrorListener() {
        return onErrorListener;
    }

    public void setOnErrorListener(OnErrorListener l) {
        this.onErrorListener = l;
    }
}
