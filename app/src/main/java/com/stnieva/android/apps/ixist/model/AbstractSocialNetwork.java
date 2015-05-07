package com.stnieva.android.apps.ixist.model;

import android.content.Context;

/**
 * Created by stnieva on 30/3/15.
 */
public abstract class AbstractSocialNetwork implements IHasError, IUpdateUsername {

    private Context context;

    private CallbackUser callback;

    public AbstractSocialNetwork(Context context, CallbackUser callback) {
        this.context = context;
        this.callback = callback;
    }

    public Context getContext() {
        return context;
    }

    protected CallbackUser getCallback() {
        return callback;
    }
}
