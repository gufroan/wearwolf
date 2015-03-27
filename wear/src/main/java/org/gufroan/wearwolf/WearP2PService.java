package org.gufroan.wearwolf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import org.gufroan.wearwolf.p2p.AbstractCommand;

public class WearP2PService extends WearableListenerService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            String action = intent.getAction();
            if (Constants.ACTION_CLICK.equals(action)) {
                sendClickToMobile();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendClickToMobile() {
        new SendClickToMobile(this).execute();
    }

    private static class SendClickToMobile extends AbstractCommand implements ResultCallback<DataApi.DataItemResult> {
        private static final String TAG = SendClickToMobile.class.getSimpleName();

        public SendClickToMobile(Context context) {
            super(context);
        }

        @Override
        public void onConnected(Bundle bundle) {
            PutDataMapRequest dataMapRequest = PutDataMapRequest.create(Constants.PATH_CLICK);
            dataMapRequest.getDataMap().putDouble(Constants.DATA_CLICK_TIMESTAMP, System.currentTimeMillis());
            PutDataRequest putDataRequest = dataMapRequest.asPutDataRequest();

            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest).setResultCallback(this);
        }

        @Override
        public void onResult(DataApi.DataItemResult dataItemResult) {
            if (!dataItemResult.getStatus().isSuccess()) {
                Log.e(TAG, "failed to send click");
            }
            mGoogleApiClient.disconnect();
        }
    }
}
