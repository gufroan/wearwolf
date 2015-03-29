package org.gufroan.wearwolf;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;

public class ActionFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    @Arg
    Intent actionIntent;
    @Arg
    int iconResource;
    @Arg
    String actionLabel;

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        if (actionLabel == null){
            actionLabel = getString(android.R.string.ok);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_action, null);

        ImageButton iconView = (ImageButton) rootView.findViewById(R.id.action_icon);
        iconView.setImageResource(iconResource);
        iconView.setOnClickListener(this);
        iconView.setOnTouchListener(this);
        TextView textView = (TextView) rootView.findViewById(R.id.action_label);
        textView.setText(actionLabel);

        rootView.setOnClickListener(this);
        rootView.setOnTouchListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (actionIntent != null) {
            getActivity().startService(actionIntent);
            showConfirmation();
            getActivity().finish();
        }
    }

    private void showConfirmation() {
        Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
        Bundle options = ActivityOptions.makeCustomAnimation(getActivity(), 0, 0).toBundle();
        getActivity().startActivity(intent, options);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            rootView.setBackgroundResource(R.color.action_fragment_pressed);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            rootView.setBackgroundResource(android.R.color.transparent);
        }
        return false;
    }
}
