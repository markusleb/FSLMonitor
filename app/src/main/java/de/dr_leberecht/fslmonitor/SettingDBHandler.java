package de.dr_leberecht.fslmonitor;


/**
 * Created by Markus Leberecht on 18.07.2015.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.Set;


public class SettingDBHandler extends SQLiteOpenHelper{
    private static final String TAG = "SettingDBHandler";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "settingsDB.db";
    public static final String TABLE_SETTINGS = "settings";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SETTINGNAME = "settingname";
    public static final String COLUMN_SETTINGVALUE= "settingvalue";

    public SettingDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"onCreate");

        String CREATE_SETTINGS_TABLE =
                "CREATE TABLE " + TABLE_SETTINGS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_SETTINGNAME + " TEXT," + COLUMN_SETTINGVALUE+ " INTEGER" + ")";
        db.execSQL(CREATE_SETTINGS_TABLE);

        // ML: populate DB with standard entries
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"onUpgrade");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS); onCreate(db);
    }

    public void addSetting(Setting setting) {
        Log.i(TAG,"addSetting");

        ContentValues values = new ContentValues();
        values.put(COLUMN_SETTINGNAME, setting.getSettingName());
        values.put(COLUMN_SETTINGVALUE, setting.getSettingValue());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SETTINGS, null, values);
        db.close(); }

    public void updateSetting(Setting setting){
        Log.i(TAG,"updateSetting");

        ContentValues values = new ContentValues();
        values.put(COLUMN_SETTINGNAME, setting.getSettingName());
        values.put(COLUMN_SETTINGVALUE, setting.getSettingValue());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_SETTINGS, values, "settingname=" + "\"" + setting.getSettingName() + "\"", null);
    }

    public Setting findSetting(String settingname) {
        Log.i(TAG,"findSetting "+ settingname);

        String query = "Select * FROM " + TABLE_SETTINGS + " WHERE " + COLUMN_SETTINGNAME+ " = \"" + settingname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Setting setting = new Setting();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            setting.setID(Integer.parseInt(cursor.getString(0)));
            setting.setSettingName(cursor.getString(1));
            setting.setSettingValue(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            setting = null;
        }
        db.close();
        Log.i(TAG,"foundSetting " + setting.getSettingName() + " value " + setting.getSettingValue() + " id " + setting.getID());

        return  setting;
    }
}
