package com.stnieva.android.apps.ixist.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stnieva.android.apps.ixist.R;

/**
 * Created by stnieva on 4/5/15.
 */
public class ErrorDialog extends DialogFragment {

    String message;

    View.OnClickListener onCancelListener;

    View.OnClickListener onTryAgainListener;

    AlertDialog dialog;

    /**
     * Override to build your own custom Dialog container.  This is typically
     * used to show an AlertDialog instead of a generic Dialog; when doing so,
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} does not need
     * to be implemented since the AlertDialog takes care of its own content.
     * <p/>
     * <p>This method will be called after {@link #onCreate(Bundle)} and
     * before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.  The
     * default implementation simply instantiates and returns a {@link Dialog}
     * class.
     * <p/>
     * <p><em>Note: DialogFragment own the {@link Dialog#setOnCancelListener
     * Dialog.setOnCancelListener} and {@link Dialog#setOnDismissListener
     * Dialog.setOnDismissListener} callbacks.  You must not set them yourself.</em>
     * To find out about these events, override {@link #onCancel(DialogInterface)}
     * and {@link #onDismiss(DialogInterface)}.</p>
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_error, null);

        TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(message);

        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(onCancelListener);

        Button tryAgain = (Button) view.findViewById(R.id.tryAgain);
        tryAgain.setOnClickListener(onTryAgainListener);

        builder.setView(view);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    public void setContent(String content) {
        this.message = content;
    }

    public void setCancelButton(View.OnClickListener l) {
        onCancelListener = l;
    }

    public void setTryAgainButton(View.OnClickListener l) {
        onTryAgainListener = l;
    }

    public void cancel() {
        dialog.cancel();
    }
}
