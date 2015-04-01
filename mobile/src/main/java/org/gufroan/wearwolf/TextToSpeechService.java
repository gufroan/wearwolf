package org.gufroan.wearwolf;

import android.app.IntentService;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.util.HashMap;

public class TextToSpeechService extends IntentService implements TextToSpeech.OnInitListener {


    private static TextToSpeech mTTS;
    private String message;

    public TextToSpeechService() {
        super("Text To Speech Service");
    }

    public void say(String message) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, String.valueOf(message.hashCode()));
        mTTS.speak(message, TextToSpeech.QUEUE_ADD, params); // Deprecated in API 21 but minSDK is 19
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS)
            say(this.message);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        message = intent.getStringExtra("message");
        if (mTTS == null) {
            mTTS = new TextToSpeech(this, this);
        } else
            say(message);
    }

    @Override
    public void onDestroy() {
        mTTS.shutdown();
        stopSelf();
        super.onDestroy();
    }
}
