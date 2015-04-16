package com.stnieva.android.apps.ixist;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stnieva.android.apps.ixist.socialnetwork.AbstractSocialNetwork;
import com.stnieva.android.apps.ixist.socialnetwork.SocialNetworkException;
import com.stnieva.android.apps.ixist.socialnetwork.SocialNetworks;
import com.stnieva.android.apps.ixist.socialnetwork.User;
import com.stnieva.android.apps.ixist.socialnetwork.facebook.Facebook;
import com.stnieva.android.apps.ixist.widget.UsersAdapter;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class MainActivity extends ActionBarActivity implements TextView.OnEditorActionListener {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout wrap = (LinearLayout) findViewById(R.id.wrap);
        wrap.post(new Runnable() {
            @Override
            public void run() {
                LayoutParams params = new LayoutParams(MATCH_PARENT, wrap.getHeight());
                wrap.setLayoutParams(params);
            }
        });

        list = (ListView) findViewById(R.id.list);
        list.post(new Runnable() {
            @Override
            public void run() {
                LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
                list.setLayoutParams(params);
            }
        });

        EditText username = (EditText) findViewById(R.id.username);
        username.setOnEditorActionListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_github) {
            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/stnieva/ixist"));
            startActivity(web);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {
                final Context context = getApplicationContext();

                SocialNetworks networks = new SocialNetworks(context, new SocialNetworks.Callback() {
                    @Override
                    public void success(List<User> users) {
                        UsersAdapter adapter = new UsersAdapter(context, users);
                        list.setAdapter(adapter);
                    }

                    @Override
                    public void failure(SocialNetworkException e) {

                    }
                });

                String username = v.getText().toString();

                if (!username.isEmpty()) {
                    networks.updateUsername(username);
                }
            }

//            handled = true;
        }

        return handled;
    }
}
