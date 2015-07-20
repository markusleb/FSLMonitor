package de.dr_leberecht.fslmonitor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.IBinder;
import android.content.Context;
import android.content.ComponentName;
import android.content.ServiceConnection;
import de.dr_leberecht.fslmonitor.DetectorService.MyLocalBinder;
import java.io.File;


/*
    With code generously inspired from https://github.com/vicktor/FreeStyleLibre-NFC-Reader
 */


public class StatusDisplayActivity extends ActionBarActivity {

    private static final String TAG = "StatusDisplay";
    private NfcAdapter mNfcAdapter;

    DetectorService detectorService;
    boolean monService_isbound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_display);
        Log.i(TAG, "onCreate");

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show();
        }

        // ML: if no database  for settings and monitor values exists, create it with standard values
        Log.i(TAG,"before DB test");
        if (!doesDatabaseExist(this,"settingsDB.db"))
            initializeDatabase();
        Log.i(TAG, "after DB test");

        // ML: if no service exists, spawn it
        Intent intent = new Intent(this, DetectorService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);

        // ML: register local broadcast receivers for service messages
        LocalBroadcastManager.getInstance(this).registerReceiver(detectorReceiver, new IntentFilter("detector-msg"));
        LocalBroadcastManager.getInstance(this).registerReceiver(sensorReadingReceiver, new IntentFilter("readsensor-msg"));

        // ML: alarm receiver instantiation
        // myReadAlarmReceiver = new readAlarmReceiver();
    }

    // ML: Handler for detector messages
    private BroadcastReceiver detectorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String action = intent.getAction();

            Log.i(TAG, "detectorReceiver action = "+ action);

            boolean sensor_detected = intent.getBooleanExtra("sensor-detected", false);
            TextView sensordetect_ref = (TextView) findViewById(R.id.sensordetect_field);
            if (sensor_detected)
                sensordetect_ref.setTextColor(Color.GREEN);
            else
                sensordetect_ref.setTextColor(Color.RED);
        }
    };

    // ML: Handler for sensor reading messages
    private BroadcastReceiver sensorReadingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String action = intent.getAction();

            Log.i(TAG, "sensorReadingReceiver action = "+ action);
            Toast.makeText(context, "Sensor data received.", Toast.LENGTH_LONG).show();

            // ML: TODO calculate BG alarm triggers
            // ML: TODO redraw diagram view
        }
    };

    private static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private void initializeDatabase() {
        Log.i(TAG,"initializeDatabase");

        // ML: get references to EditText resources in settings activity
        final EditText sampleinterval_value_ref = (EditText) findViewById(R.id.sampleinterval_value);
        final EditText runningavg_value_ref = (EditText) findViewById(R.id.runningavg_value);
        final EditText interstitialbgdelay_value_ref = (EditText) findViewById(R.id.interstitialbgdelay_value);
        final EditText futureprojectiondelay_value_ref = (EditText) findViewById(R.id.futureprojectiondelay_value);
        final EditText currentbgalarm_value_ref = (EditText)findViewById(R.id.currentbgalarm_value);
        final EditText futurebgalarm_value_ref = (EditText) findViewById(R.id.futurebgalarm_value);

        Log.i(TAG,"have references to EditText");

        // ML: put text into charsequences
        CharSequence sampleinterval_value_string = "5";
        CharSequence runningavg_value_string = "10";
        CharSequence interstitialbgdelay_value_string = "20";
        CharSequence futureprojectiondelay_value_string = "30";
        CharSequence currentbgalarm_value_string = "80";
        CharSequence futurebgalarm_value_string = "120";

        Log.i(TAG,"have text from Text fields");

        // ML: save key value pairs of EditText views
        Setting sampleinterval_setting = new Setting(),
                runningavg_setting = new Setting(),
                interstitialbgdelay_setting = new Setting(),
                futureprojectiondelay_setting = new Setting(),
                currentbgalarm_setting = new Setting(),
                futurebgalarm_setting = new Setting();

        sampleinterval_setting.setSettingName("sampleinterval");
        sampleinterval_setting.setSettingValue(Integer.parseInt(sampleinterval_value_string.toString()));
        runningavg_setting.setSettingName("runningavg");
        runningavg_setting.setSettingValue(Integer.parseInt(runningavg_value_string.toString()));
        interstitialbgdelay_setting.setSettingName("interstitialbgdelay");
        interstitialbgdelay_setting.setSettingValue(Integer.parseInt(interstitialbgdelay_value_string.toString()));
        futureprojectiondelay_setting.setSettingName("futureprojectiondelay");
        futureprojectiondelay_setting.setSettingValue(Integer.parseInt(futureprojectiondelay_value_string.toString()));
        currentbgalarm_setting.setSettingName("currentbgalarm");
        currentbgalarm_setting.setSettingValue(Integer.parseInt(currentbgalarm_value_string.toString()));
        futurebgalarm_setting.setSettingName("futurebgalarm");
        futurebgalarm_setting.setSettingValue(Integer.parseInt(futurebgalarm_value_string.toString()));

        Log.i(TAG,"before SettingDBHandler");

        SettingDBHandler sdb = new SettingDBHandler(this, null, null, 1);
        sdb.addSetting(sampleinterval_setting);
        sdb.addSetting(runningavg_setting);
        sdb.addSetting(interstitialbgdelay_setting);
        sdb.addSetting(futureprojectiondelay_setting);
        sdb.addSetting(currentbgalarm_setting);
        sdb.addSetting(futurebgalarm_setting);
    }

    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocalBinder binder = (MyLocalBinder) service;
            detectorService = binder.getService();
            monService_isbound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            monService_isbound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void  onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart");
    }

    @Override
    protected void onDestroy(){
        // ML: clear alarm
        this.monitorOffClick(findViewById(R.id.monitor_off_button));

        // ML: Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(sensorReadingReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(detectorReceiver);

        // ML: unbind from detector service
        if (monService_isbound)
            getApplicationContext().unbindService(myConnection);
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            // ML: invoke SettingsActivity
            Intent i = new Intent(this,SettingsActivity.class);
            Log.i(TAG, "starting SettingsActivity");
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void monitorOnClick(View clickview){
        // ML: steps to be executed when Monitor On radio button is pressed
        Log.i(TAG, "monitorOnClick");

        // ML: start repeating alarm for periodic service call to MonitorIntentService
        Intent intent = new Intent(this.getApplicationContext(),readAlarmReceiver.class);

        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 30169,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // ML: TODO Setup periodic alarm every XX minutes according to setting

        SettingDBHandler sdb = new SettingDBHandler(this, null, null,1);
        int sampleinterval_mins = (sdb.findSetting("sampleinterval")).getSettingValue();

        long firstMillis = System.currentTimeMillis(); // first run of alarm is immediate
        int intervalMillis = sampleinterval_mins*60*1000;
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Log.i(TAG, "intervalMillis = "+intervalMillis+ "   alarm = " + alarm.toString());

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);
    }

    public void monitorOffClick(View clickview){
        // ML: steps to be executed when Monitor Off radio button is pressed
        Log.i(TAG,"monitorOffClick");

        // ML: cancel repeating alarm
        Intent intent = new Intent(this.getApplicationContext(),readAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 30169,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }




}
