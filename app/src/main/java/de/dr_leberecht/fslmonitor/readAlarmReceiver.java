package de.dr_leberecht.fslmonitor;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Markus Leberecht on 20.07.2015.
 */

public class readAlarmReceiver extends BroadcastReceiver {
    public static final String ACTION = "de.dr_leberecht.fslmonitor.action.READSENSOR";

    final String TAG="readAlarmReceiver";

    public readAlarmReceiver(){
        super();
    }

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "readsensor alarm triggered");
        Intent i = new Intent(context, MonitorIntentService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}