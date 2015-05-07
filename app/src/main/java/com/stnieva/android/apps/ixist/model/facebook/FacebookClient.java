package com.stnieva.android.apps.ixist.model.facebook;

import android.os.AsyncTask;

import com.stnieva.android.apps.ixist.model.CallbackUser;
import com.stnieva.android.apps.ixist.model.SocialNetworkException;
import com.stnieva.android.apps.ixist.model.Type;
import com.stnieva.android.apps.ixist.model.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by stnieva on 1/4/15.
 */
public class FacebookClient extends AsyncTask<String, Void, JSONObject> {

    private CallbackUser callback;

    public FacebookClient(CallbackUser callback) {
        this.callback = callback;
    }

    @Override
    protected JSONObject doInBackground(String... userNames) {
        HttpClient client = new DefaultHttpClient();

        HttpGet get = new HttpGet(String.format("https://graph.facebook.com/v1.0/%s", Arrays.asList(userNames).get(0)));

        JSONObject json = null;

        try {
            HttpResponse response = client.execute(get);

            HttpEntity entity = response.getEntity();

            json = new JSONObject(EntityUtils.toString(entity));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);

        try {
            User user;

            if (json.has("error")) {
                user = new User(
                        Type.FACEBOOK,
                        false,
                        null,
                        null,
                        null
                );

                callback.onFailure(new SocialNetworkException("The username you requested do not exist"));
                callback.onSuccess(user);
            } else {
                user = new User(
                        Type.FACEBOOK,
                        true,
                        json.getString("id"),
                        json.getString("username"),
                        json.getString("name")
                );

                callback.onSuccess(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
