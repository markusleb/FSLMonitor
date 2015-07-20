package de.dr_leberecht.fslmonitor;

import java.util.Set;

/**
 * Created by Markus Leberecht on 18.07.2015.
 */

public class Setting {
    private int _id;
    private String _setting_name;
    private int _setting_value;

    public Setting(){

    }

    public Setting(int id, String setting_name, int setting_value){
        this._id = id;
        this._setting_name = setting_name;
        this._setting_value = setting_value;
    }

    public Setting(String setting_name, int setting_value){
        this._setting_name = setting_name;
        this._setting_value = setting_value;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getID(){
        return this._id;
    }

    public void setSettingName(String setting_name){
        this._setting_name = setting_name;
    }

    public String getSettingName() {
        return this._setting_name;
    }

    public void setSettingValue(int setting_value){
        this._setting_value = setting_value;
    }

    public int getSettingValue(){
        return this._setting_value;
    }
}
