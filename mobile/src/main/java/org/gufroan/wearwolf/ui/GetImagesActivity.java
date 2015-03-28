package org.gufroan.wearwolf.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import org.gufroan.wearwolf.Constants;
import org.gufroan.wearwolf.ImageFetchingService;
import org.gufroan.wearwolf.R;

/**
 * Created by vitaliyistomov on 28/03/15.
 */
public class GetImagesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_images);

        findViewById(R.id.get_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent service = new Intent(GetImagesActivity.this, ImageFetchingService.class);
                startService(service);
            }
        });

        final TextView label = (TextView) findViewById(R.id.done_label);
        final BroadcastReceiver rcvr = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                label.setText("Done");
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(rcvr, new IntentFilter(Constants.ACTION_DONE_LOADING_IMAGES));
    }
}
