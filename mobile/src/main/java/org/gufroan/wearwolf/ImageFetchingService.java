package org.gufroan.wearwolf;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import org.gufroan.wearwolf.data.pojo.ResponseRoot;
import org.gufroan.wearwolf.data.pojo.Result;
import org.gufroan.wearwolf.network.NetworkApi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by vitaliyistomov on 28/03/15.
 */
public class ImageFetchingService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ImageFetchingService() {
        super("qwe");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final List<String> cardLabels = NavigationEngine.getValues();
        final File tmpDir = new File(Environment.getExternalStorageDirectory() + "/wearwolf");
        if (tmpDir.exists()) {
            for (File file : tmpDir.listFiles()) {
                file.delete();
            }
        } else {
            tmpDir.mkdir();
        }

        for (final String label : cardLabels) {
            findAndFetchImage(label, 0);
        }

        final Intent doneIntent = new Intent(Constants.ACTION_DONE_LOADING_IMAGES);
        LocalBroadcastManager.getInstance(this).sendBroadcast(doneIntent);
    }

    private void findAndFetchImage(final String label, final int pos) {
        final ResponseRoot resp = NetworkApi.getInstance().triggerSearchRequest(label, pos);
        final List<Result> results = resp.getResponseData().getResults();
        if (results != null && results.size() > 0) {
            System.out.println("=============== downloading [" + label + "|" + pos + "]: " + results.get(0).getUrl());
            try {
                downloadFile(results.get(0).getUrl(), label);
            } catch (final Exception e) {
                System.out.println("=============== downloading ECXEPTION [" + label + "|" + pos + "]: " + results.get(0).getUrl());
                findAndFetchImage(label, pos + 1);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void downloadFile(final String imageUrl, final String label) throws IOException {
        int count;
//        try {
            final URL url = new URL(imageUrl);
            final URLConnection connection = url.openConnection();
            connection.connect();

            final InputStream input = new BufferedInputStream(url.openStream(), 8192);
            final OutputStream output = new FileOutputStream(
//                    getFilesDir()
                    Environment.getExternalStorageDirectory()
                            .toString() +
                            "/wearwolf" +
                            "/" + label);
            byte data[] = new byte[1024];

            while ((count = input.read(data)) != -1) {
                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();
//        } catch (Exception e) {
//            Log.e("Error: ", e.getMessage());
//        }
    }
}
