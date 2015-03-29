package org.gufroan.p2p;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import org.gufroan.wearwolf.Constants;
import org.gufroan.wearwolf.data.Part;

public class UpdateCommand extends AbstractCommand implements ResultCallback<DataApi.DataItemResult> {
    private static final String TAG = UpdateCommand.class.getSimpleName();
    private final String pathForData;
    private final Part data;

    public UpdateCommand(Context context, final String pathForData, final Part data) {
        super(context);
        this.pathForData = pathForData;
        this.data = data;
    }

    @Override
    public void onConnected(Bundle bundle) {
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create(Constants.PATH_NODE + "/" + pathForData);
        dataMapRequest.getDataMap().putString(Constants.MESSAGE_KEY, data.getStringData());
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
