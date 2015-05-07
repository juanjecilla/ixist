package com.stnieva.android.apps.ixist.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stnieva.android.apps.ixist.R;
import com.stnieva.android.apps.ixist.model.User;

import java.util.List;

/**
 * Created by stnieva on 9/4/15.
 */
public class UsersAdapter extends ArrayAdapter<User> {

    private Context context;

    private List<User> users;

    public UsersAdapter(Context context, List<User> users) {
        super(context, R.layout.list_item, users);

        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View user;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            user = inflater.inflate(R.layout.list_item, null);
            user.setTag(user);
        } else {
            user = (View) convertView.getTag();
        }

        ImageView icon = (ImageView) user.findViewById(R.id.icon);
        switch (users.get(position).getType()) {
            case FACEBOOK:
                icon.setImageResource(R.drawable.ic_facebook);
                break;

            case TWITTER:
                icon.setImageResource(R.drawable.ic_twitter);
                break;
        }

        TextView title = (TextView) user.findViewById(R.id.title);
        title.setText(users.get(position).getName());

        TextView caption = (TextView) user.findViewById(R.id.caption);
        caption.setText(users.get(position).getUsername());

        View line = user.findViewById(R.id.line);
        int visibility = users.get(position).getType() == users.get(users.size() - 1).getType() ? View.GONE : View.VISIBLE;
        line.setVisibility(visibility);

        return user;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
