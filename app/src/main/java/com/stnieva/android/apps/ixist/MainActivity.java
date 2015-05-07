package com.stnieva.android.apps.ixist;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.stnieva.android.apps.ixist.model.CallbackUsers;
import com.stnieva.android.apps.ixist.model.SocialNetworkException;
import com.stnieva.android.apps.ixist.model.SocialNetworks;
import com.stnieva.android.apps.ixist.model.User;
import com.stnieva.android.apps.ixist.view.ErrorDialog;
import com.stnieva.android.apps.ixist.view.ProgressBar;
import com.stnieva.android.apps.ixist.view.UsersAdapter;
import com.stnieva.android.apps.ixist.view.WarningDialog;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class MainActivity extends ActionBarActivity {

    private LinearLayout wrapUsername;

    private EditText username;

    private LinearLayout wrapList;

    private ListView list;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wrapUsername = (LinearLayout) findViewById(R.id.wrapUsername);
        wrapUsername.post(new Runnable() {
            @Override
            public void run() {
                LayoutParams params = new LayoutParams(MATCH_PARENT, wrapUsername.getHeight());
                wrapUsername.setLayoutParams(params);
            }
        });

        username = (EditText) findViewById(R.id.username);
        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    update();
                }

                return handled;
            }
        });

        wrapList = (LinearLayout) findViewById(R.id.wrapList);

        list = (ListView) findViewById(R.id.list);
        list.post(new Runnable() {
            @Override
            public void run() {
                LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
                list.setLayoutParams(params);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void tryAgain() {
        FragmentManager fm = getSupportFragmentManager();

        final ErrorDialog dialog = new ErrorDialog();

        dialog.setContent("Problem with network connection. Please try again.");

        dialog.setCancelButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.setTryAgainButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                update();
            }
        });

        dialog.show(fm, "error");
    }

    private void warning() {
        FragmentManager fm = getSupportFragmentManager();

        final WarningDialog dialog = new WarningDialog();

        dialog.setContent("Username is required and can't be empty.");

        dialog.setOkButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show(fm, "warning");
    }

    private void update() {
        if (username.getText().toString().isEmpty()) {
            warning();
        } else if (isConnected()) {
            progressBar.start();

            final Context context = getApplicationContext();

            SocialNetworks networks = new SocialNetworks(context, new CallbackUsers() {
                @Override
                public void success(List<User> users) {
                    UsersAdapter adapter = new UsersAdapter(context, users);
                    list.setAdapter(adapter);

                    progressBar.stop();
                }

                @Override
                public void failure(SocialNetworkException e) {

                }
            });

            networks.updateUsername(username.getText().toString());
        } else {
            tryAgain();
        }
    }
}
