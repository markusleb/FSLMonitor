package de.dr_leberecht.fslmonitor;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingsActivity extends ActionBarActivity {

    private static final String TAG = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        Log.i(TAG, "setting view to Settings");

        setContentView(R.layout.activity_settings);
        // ML: gather setting values from database and set as content in EditText resources

       SettingDBHandler sdb = new SettingDBHandler(this, null, null,1);

        // ML: get references to EditText resources in settings activity
        EditText sampleinterval_value_ref = (EditText) findViewById(R.id.sampleinterval_value);
        EditText runningavg_value_ref = (EditText) findViewById(R.id.runningavg_value);
        EditText interstitialbgdelay_value_ref = (EditText) findViewById(R.id.interstitialbgdelay_value);
        EditText futureprojectiondelay_value_ref = (EditText) findViewById(R.id.futureprojectiondelay_value);
        EditText currentbgalarm_value_ref = (EditText)findViewById(R.id.currentbgalarm_value);
        EditText futurebgalarm_value_ref = (EditText) findViewById(R.id.futurebgalarm_value);

        // ML: get saved string out of database
        CharSequence sampleinterval_value_string = Integer.toString((sdb.findSetting("sampleinterval")).getSettingValue());
        CharSequence runningavg_value_string = Integer.toString((sdb.findSetting("runningavg")).getSettingValue());
        CharSequence interstitialbgdelay_value_string = Integer.toString((sdb.findSetting("interstitialbgdelay")).getSettingValue());
        CharSequence futureprojectiondelay_value_string = Integer.toString((sdb.findSetting("futureprojectiondelay")).getSettingValue());
        CharSequence currentbgalarm_value_string = Integer.toString((sdb.findSetting("currentbgalarm")).getSettingValue());
        CharSequence futurebgalarm_value_string = Integer.toString((sdb.findSetting("futurebgalarm")).getSettingValue());

        // ML: restore back text into views

        sampleinterval_value_ref.setText(sampleinterval_value_string);
        runningavg_value_ref.setText(runningavg_value_string);
        interstitialbgdelay_value_ref.setText(interstitialbgdelay_value_string);
        futureprojectiondelay_value_ref.setText(futureprojectiondelay_value_string);
        currentbgalarm_value_ref.setText(currentbgalarm_value_string);
        futurebgalarm_value_ref.setText(futurebgalarm_value_string);
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }*/


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");

        // ML: get references to EditText resources in settings activity
        final EditText sampleinterval_value_ref = (EditText) findViewById(R.id.sampleinterval_value);
        final EditText runningavg_value_ref = (EditText) findViewById(R.id.runningavg_value);
        final EditText interstitialbgdelay_value_ref = (EditText) findViewById(R.id.interstitialbgdelay_value);
        final EditText futureprojectiondelay_value_ref = (EditText) findViewById(R.id.futureprojectiondelay_value);
        final EditText currentbgalarm_value_ref = (EditText)findViewById(R.id.currentbgalarm_value);
        final EditText futurebgalarm_value_ref = (EditText) findViewById(R.id.futurebgalarm_value);

        // ML: get text of EditText Views
        CharSequence sampleinterval_value_string = sampleinterval_value_ref.getText();
        CharSequence runningavg_value_string = runningavg_value_ref.getText();
        CharSequence interstitialbgdelay_value_string = interstitialbgdelay_value_ref.getText();
        CharSequence futureprojectiondelay_value_string = futureprojectiondelay_value_ref.getText();
        CharSequence currentbgalarm_value_string = currentbgalarm_value_ref.getText();
        CharSequence futurebgalarm_value_string = futurebgalarm_value_ref.getText();

        // ML: save key value pairs of EditText views
        outState.putCharSequence("sampleinterval", sampleinterval_value_string);
        outState.putCharSequence("runningavg", runningavg_value_string);
        outState.putCharSequence("interstitialbgdelay", interstitialbgdelay_value_string);
        outState.putCharSequence("futureprojectiondelay", futureprojectiondelay_value_string);
        outState.putCharSequence("currentbgalarm", currentbgalarm_value_string);
        outState.putCharSequence("futurebgalarm", futurebgalarm_value_string);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");

        // ML: get references to EditText resources in settings activity
        final EditText sampleinterval_value_ref = (EditText) findViewById(R.id.sampleinterval_value);
        final EditText runningavg_value_ref = (EditText) findViewById(R.id.runningavg_value);
        final EditText interstitialbgdelay_value_ref = (EditText) findViewById(R.id.interstitialbgdelay_value);
        final EditText futureprojectiondelay_value_ref = (EditText) findViewById(R.id.futureprojectiondelay_value);
        final EditText currentbgalarm_value_ref = (EditText)findViewById(R.id.currentbgalarm_value);
        final EditText futurebgalarm_value_ref = (EditText) findViewById(R.id.futurebgalarm_value);

        // ML: get saved string out of Bundle key value pair
        CharSequence sampleinterval_value_string = savedInstanceState.getCharSequence("sampleinterval");
        CharSequence runningavg_value_string = savedInstanceState.getCharSequence("runningavg");
        CharSequence interstitialbgdelay_value_string = savedInstanceState.getCharSequence("interstitialbgdelay");
        CharSequence futureprojectiondelay_value_string = savedInstanceState.getCharSequence("futureprojectiondelay");
        CharSequence currentbgalarm_value_string = savedInstanceState.getCharSequence("currentbgalarm");
        CharSequence futurebgalarm_value_string = savedInstanceState.getCharSequence("futurebgalarm");

        // ML: restore back text into views
        sampleinterval_value_ref.setText(sampleinterval_value_string);
        runningavg_value_ref.setText(runningavg_value_string);
        interstitialbgdelay_value_ref.setText(interstitialbgdelay_value_string);
        futureprojectiondelay_value_ref.setText(futureprojectiondelay_value_string);
        currentbgalarm_value_ref.setText(currentbgalarm_value_string);
        futurebgalarm_value_ref.setText(futurebgalarm_value_string);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveSettings(View clickview){
        // ML: stuff to be done when Save button has been pressed
        Log.i(TAG, "saveSettings");

        // ML: get references to EditText resources in settings activity
        final EditText sampleinterval_value_ref = (EditText) findViewById(R.id.sampleinterval_value);
        final EditText runningavg_value_ref = (EditText) findViewById(R.id.runningavg_value);
        final EditText interstitialbgdelay_value_ref = (EditText) findViewById(R.id.interstitialbgdelay_value);
        final EditText futureprojectiondelay_value_ref = (EditText) findViewById(R.id.futureprojectiondelay_value);
        final EditText currentbgalarm_value_ref = (EditText)findViewById(R.id.currentbgalarm_value);
        final EditText futurebgalarm_value_ref = (EditText) findViewById(R.id.futurebgalarm_value);

        // ML: get text of EditText Views
        CharSequence sampleinterval_value_string = sampleinterval_value_ref.getText();
        CharSequence runningavg_value_string = runningavg_value_ref.getText();
        CharSequence interstitialbgdelay_value_string = interstitialbgdelay_value_ref.getText();
        CharSequence futureprojectiondelay_value_string = futureprojectiondelay_value_ref.getText();
        CharSequence currentbgalarm_value_string = currentbgalarm_value_ref.getText();
        CharSequence futurebgalarm_value_string = futurebgalarm_value_ref.getText();

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

        SettingDBHandler sdb = new SettingDBHandler(this, null, null, 1);
        sdb.updateSetting(sampleinterval_setting);
        sdb.updateSetting(runningavg_setting);
        sdb.updateSetting(interstitialbgdelay_setting);
        sdb.updateSetting(futureprojectiondelay_setting);
        sdb.updateSetting(currentbgalarm_setting);
        sdb.updateSetting(futurebgalarm_setting);


        // ML: TODO retrigger monitor service

        // ML: end this activity, return to StatusDisplayActivity
        this.finish();
    }

    public void cancelSettings(View clickview){
        // ML: stuff to be done when pressing Cancel button
        Log.i(TAG,"cancelSettings");

        // ML: end this activity, return to StatusDisplayActivity
        this.finish();
    }

    public void resetSettings(View clickview){
        // ML: stuff to be done when Reset button has been pressed
        Log.i(TAG, "resetSettings");

        // ML: get text of EditText Views
        CharSequence sampleinterval_value_string = "5";
        CharSequence runningavg_value_string = "10";
        CharSequence interstitialbgdelay_value_string = "20";
        CharSequence futureprojectiondelay_value_string = "30";
        CharSequence currentbgalarm_value_string = "80";
        CharSequence futurebgalarm_value_string = "120";

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

        SettingDBHandler sdb = new SettingDBHandler(this, null, null, 1);
        sdb.updateSetting(sampleinterval_setting);
        sdb.updateSetting(runningavg_setting);
        sdb.updateSetting(interstitialbgdelay_setting);
        sdb.updateSetting(futureprojectiondelay_setting);
        sdb.updateSetting(currentbgalarm_setting);
        sdb.updateSetting(futurebgalarm_setting);


        // ML: TODO retrigger monitor service

        // ML: end this activity, return to StatusDisplayActivity
        this.finish();
    }
}
