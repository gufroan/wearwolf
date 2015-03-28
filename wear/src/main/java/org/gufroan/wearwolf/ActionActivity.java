package org.gufroan.wearwolf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ActionActivity extends Activity {
    public static final String INTENT = "intent";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        getFragmentManager().beginTransaction().add(R.id.container, ActionFragmentBuilder.newActionFragment((Intent) getIntent().getParcelableExtra(INTENT), "ok", R.drawable.action_button)).commit();
    }
}
