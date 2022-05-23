package com.example.viewall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;*/

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vaibhav.
 */

public class SharePrefrancClass {

    private static SharePrefrancClass ourInstance;
    private static Context context;
    SharedPreferences preference;

    public final static String PREFERENCE_NAME = "MyPref";

    public static SharePrefrancClass getInstance(Context mcontext) {
        context = mcontext;
        if (ourInstance == null) {
            ourInstance = new SharePrefrancClass();
        }
        return ourInstance;
    }

    public SharePrefrancClass() {
        preference = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    /**
     * savePref() for save
     *
     * @param key,value Key value of Shared Prefrance
     */
    public void savePref(String key, String val) {
        if (preference != null) {
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(key, val);
            editor.apply();
        }
    }

    public void saveListPref(String key, Set set) {
        if (preference != null) {
            SharedPreferences.Editor editor = preference.edit();
            editor.putStringSet(key, set);
            editor.apply();
        }
    }


    /*public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        savePref(key, json);
    }
    public void saveObject(String key,Map object) {
        if (preference != null) {
            SharedPreferences.Editor prefsEditor = preference.edit();
            Gson gson = new Gson();
           // String json = gson.toJson(object);
            String json =  new Gson().toJson(new HashMap(object));
//            prefsEditor.putString(key, object);
         //   prefsEditor.apply();
        }
    }*/

    /*TO RETRIEVE
    * Gson gson = new Gson();
String json = mPrefs.getString("MyObject", "");
MyObject obj = gson.fromJson(json, MyObject.class);*/

    /**
     * setBoolean() for set
     *
     * @param key,b Key value of Shared Prefrance
     */
    public void setPrefrance(String key, boolean b) {
        if (preference != null) {
            SharedPreferences.Editor editor = preference.edit();
            editor.putBoolean(key, b);
            editor.apply();
        }
    }

    /**
     * clearPrefra() for delete
     *
     * @param key Key value of Shared Prefrance
     */
    public void clearPref(String key) {
        if (preference != null) {
            SharedPreferences.Editor editor = preference.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    /**
     * getString() for use
     *
     * @param key Key value of Shared Prefrance
     * @return
     */

    public String getPref(String key) {
        if (preference != null) {
            return preference.getString(key, null);
        }
        return null;
    }

   /* public Object getObject(String key) {
        if (preference != null) {
            //SharedPreferences.Editor prefsEditor = preference.edit();
            Gson gson = new Gson();
            Map obj = gson.fromJson(key, Map.class);
            //prefsEditor.putString(key, json);
            //prefsEditor.apply();
            return obj;
        }
        return null;
    }*/

    public Set getListPref(String key,Set set) {
        if (preference != null) {
            return preference.getStringSet(key, set);
        }
        return null;
    }

    /**
     * getBoolean() for use
     *
     * @param name Key value of Shared Prefrance
     * @return
     */
    public boolean hasPreference(String name) {
        if (preference != null) {
            return preference.getBoolean(name, false);
        }
        return false;
    }


    public Boolean getBoolean(String key) {
        if (preference != null) {
            return preference.getBoolean(key, false);
        }
        return null;
    }

    /*public void saveList(List<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public List<String> getList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }*/

/*
    public void saveArrayList(ArrayList<CouponModel> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }
*/

    /*public ArrayList<CouponModel> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<CouponModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/

//    public void saveObject(String key,Object object) {
//        if (preference != null) {
//            SharedPreferences.Editor prefsEditor = preference.edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(object);
//            prefsEditor.putString(key, json);
//            prefsEditor.apply();
//        }
//    }

}
