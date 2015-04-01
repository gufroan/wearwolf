package org.gufroan.wearwolf;

import android.content.Intent;
import android.speech.tts.TextToSpeech;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MobileP2PService extends WearableListenerService {

    private GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                if (Constants.PATH_CLICK.equals(dataEvent.getDataItem().getUri().getPath())) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(dataEvent.getDataItem());
                    String msg = dataMapItem.getDataMap().getString(Constants.MESSAGE_KEY);
                    processMessage(msg); // fixme
                }
            }
        }
    }

    private void processMessage(String msg) {
        Intent ttsIntent = new Intent(getApplicationContext(), TextToSpeechService.class);
        ttsIntent.putExtra("message", msg);
        startService(ttsIntent);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (!googleApiClient.isConnected())
            googleApiClient.blockingConnect();

        if (Constants.ERROR_PATH.equals(messageEvent.getPath())) {
            DataMap map = DataMap.fromByteArray(messageEvent.getData());

            ByteArrayInputStream bis = new ByteArrayInputStream(map.getByteArray(Constants.DATA_EXCEPTION));
            try {
                ObjectInputStream ois = new ObjectInputStream(bis);
                Throwable ex = (Throwable) ois.readObject();
                throw new RuntimeException(ex);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
