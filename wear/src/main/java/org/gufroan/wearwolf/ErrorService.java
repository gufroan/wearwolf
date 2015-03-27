package org.gufroan.wearwolf;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorService extends IntentService {
    public ErrorService() {
        super("ErrorService");
    }

    private List<String> getNodes(GoogleApiClient mGoogleApiClient) {
        ArrayList<String> results = new ArrayList<String>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }
        return results;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        GoogleApiClient mGoogleAppiClient = new GoogleApiClient.Builder(ErrorService.this)
                .addApi(Wearable.API)
                .build();
        mGoogleAppiClient.blockingConnect();

        List<String> nodes = getNodes(mGoogleAppiClient);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(intent.getSerializableExtra(Constants.EXTRA_EXCEPTION));
            byte[] exceptionData = bos.toByteArray();

            DataMap dataMap = new DataMap();
            dataMap.putByteArray(Constants.DATA_EXCEPTION, exceptionData);

            // Add a bit of information on the Wear Device to pass a long with the exception
            dataMap.putString(Constants.DATA_BOARD, Build.BOARD);
            dataMap.putString(Constants.DATA_FINGERPRINT, Build.FINGERPRINT);
            dataMap.putString(Constants.DATA_MODEL, Build.MODEL);
            dataMap.putString(Constants.DATA_MANUFACTURER, Build.MANUFACTURER);
            dataMap.putString(Constants.DATA_PRODUCT, Build.PRODUCT);


            // "Fire and forget" send to connected Smartphone/Tablet using the Wearable Message API
            Wearable.MessageApi.sendMessage(mGoogleAppiClient, nodes.get(0), Constants.ERROR_PATH, dataMap.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (IOException exx) {
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException exx) {
                // ignore close exception
            }
        }
    }
}
