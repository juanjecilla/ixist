package com.stnieva.android.apps.ixist.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.stnieva.android.apps.ixist.R;

/**
 * Created by stnieva on 5/5/15.
 */
public class ProgressBar extends LinearLayout {

    View root;

    View progress;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        root = inflater.inflate(R.layout.bar_progress, this);
        root.setVisibility(INVISIBLE);

        progress = root.findViewById(R.id.progress);
    }

    public void start() {
        root.setVisibility(VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.progress);
        progress.startAnimation(animation);
    }

    public void stop() {
        root.setVisibility(INVISIBLE);

        progress.clearAnimation();
    }
}
