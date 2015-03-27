package org.gufroan.wearwolf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

public class SwitchActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                initUi(stub);
            }
        });
    }

    private void initUi(final WatchViewStub stub) {
        mTextView = (TextView) stub.findViewById(R.id.text);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent wearP2PIntent = new Intent(SwitchActivity.this, WearP2PService.class);
                wearP2PIntent.setAction(Constants.ACTION_CLICK);
                startService(wearP2PIntent);
            }
        });
    }
}
