package de.dr_leberecht.fslmonitor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class DetectorService extends Service {
    private final IBinder myBinder = new MyLocalBinder();
    private static final String TAG = "DetectorService";
    private Thread monitor_thread;

    public DetectorService() {
        Log.i(TAG, "Service constructor");

        // ML: spawn detection thread
        final Context context = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "detectionLoop started");
                int count = 0;
                Intent intent = new Intent("detector-msg");

                while (true) {
                    count++;
                    Log.i(TAG," count = " + count);

                    if (count%2 == 1)
                        intent.putExtra("sensor-detected", true);
                    else
                        intent.putExtra("sensor-detected", false);

                    final boolean b = LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // ML: TODO read NFC status every five seconds
                // ML: TODO send message to UI thread to update status
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyLocalBinder extends Binder {
        DetectorService getService() {
            return DetectorService.this;
        }
    }

}
