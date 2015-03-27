package org.gufroan.wearwolf;

import android.app.Application;
import android.content.Intent;

public class WearApplication extends Application {
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    private Thread.UncaughtExceptionHandler wearUncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(final Thread thread, final Throwable ex) {

            sendToMobileDevice(ex);

            uncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    };

    private void sendToMobileDevice(Throwable ex) {
        Intent errorIntent = new Intent(this, ErrorService.class);
        errorIntent.putExtra(Constants.EXTRA_EXCEPTION, ex);
        startService(errorIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(wearUncaughtExceptionHandler);
    }

}
