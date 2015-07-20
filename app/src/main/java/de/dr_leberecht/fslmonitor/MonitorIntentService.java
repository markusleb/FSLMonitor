package de.dr_leberecht.fslmonitor;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class MonitorIntentService extends IntentService {
    private static final String TAG = "MonitorIntentService";
    private static final String ACTION_READSENSOR = "de.dr_leberecht.fslmonitor.action.READSENSOR";
    private static final String ACTION_UNDEFINED = "de.dr_leberecht.fslmonitor.action.UNDEFINED";

    /*
    private static final String EXTRA_SAMPLEINTERVAL= "sampleinterval";
    private static final String EXTRA_RUNNINGAVG = "runningavg";
    private static final String EXTRA_INTERSTITIALBGDELAY = "interstitialbgdelay";
    private static final String EXTRA_FUTUREPROJECTIONDELAY = "futureprojectiondelay";
    private static final String EXTRA_CURRENTBGALARM = "currentbgalarm";
    private static final String EXTRA_FUTUREBGALARM = "futurebgalarm";
    */

    // TODO: Customize helper method
    public static void startReadSensor(Context context) {
        Intent intent = new Intent(context, MonitorIntentService.class);
        intent.setAction(ACTION_READSENSOR);
        /*
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        */
        context.startService(intent);
    }

    public MonitorIntentService() {
        super("MonitorIntentService");
        Log.i(TAG, "has been constructed");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"onHandleIntent");
        if (intent != null)
            handleReadSensor();
    }

    private void handleReadSensor() {
        // TODO: Implement reading and interpreting sensor data in separate thread

        final Context context = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "readSensor started");
                Date date = new Date();
                Intent intent = new Intent("readsensor-msg");

                // ML: TODO read block of NFC data

                // ML: TODO put relevant data into service-msg intent and issue broadcast

                intent.putExtra("bg-timestamp", date.toString());
                intent.putExtra("bg-data", "");
                final boolean b = LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        }).start();

        // throw new UnsupportedOperationException("Not yet implemented");

    }
}
