package com.stnieva.android.apps.ixist.socialnetwork.facebook;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
public class UserId extends AsyncTask<String, Void, String> {

        private OnFinishListener onFinishListener;;

    public UserId(OnFinishListener l) {
        this.onFinishListener = l;
    }

    @Override
    protected String doInBackground(String... userNames) {
        HttpClient client = new DefaultHttpClient();

        HttpGet get = new HttpGet(String.format("https://graph.facebook.com/v1.0/%s", Arrays.asList(userNames).get(0)));

        String userId = null;

        try {
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();

                JSONObject json = new JSONObject(EntityUtils.toString(entity));

                if (json.has("id")) {
                    userId = json.getString("id");
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return userId;
    }

    @Override
    protected void onPostExecute(String userId) {
        super.onPostExecute(userId);
        onFinishListener.onFinish(userId);
    }

public interface OnFinishListener  {
        public void onFinish(String userId);
    }
}
