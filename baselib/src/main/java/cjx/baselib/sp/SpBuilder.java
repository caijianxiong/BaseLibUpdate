package cjx.baselib.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Set;

import cjx.baselib.LibApplication;

public class SpBuilder extends AbsBuilder {

    private HashMap<String, SharedPreferences> sps;
    private SharedPreferences currentSp;
    private SharedPreferences.Editor editor;

    public SpBuilder() {
        String defaultMarkName = "user_sp";
        SharedPreferences defaultSp = LibApplication.getAppContext().getSharedPreferences(defaultMarkName, Context.MODE_PRIVATE);
        sps = new HashMap<>();
        sps.put(defaultMarkName, defaultSp);
        currentSp = defaultSp;
        editor = currentSp.edit();
    }


    @Override
    public void commit() {
        editor.commit();//立即执行  apply提交到线程中执行
    }

    /**
     * 存取值申明本次存取操作是那个表，取不到值可能是上次操作表，，没有切换表
     *
     * @param markName
     * @return
     */
    public SpBuilder spMarkName(String markName) {
        if (sps.containsKey(markName)) {
            currentSp = sps.get(markName);
        } else {
            currentSp = LibApplication.getAppContext().getSharedPreferences(markName, Context.MODE_PRIVATE);
            sps.put(markName, currentSp);
        }
        editor = currentSp.edit();
        return this;
    }

    public  SpBuilder putString(String key, String value) {
        editor.putString(key, value);
        return this;
    }


    public SpBuilder putStringSet(String key, Set<String> values) {
        editor.putStringSet(key, values);
        return this;
    }


    public SpBuilder putFloat(String key, float value) {
        editor.putFloat(key, value);
        return this;
    }


    public SpBuilder putLong(String key, long value) {
        editor.putLong(key, value);
        return null;
    }


    public SpBuilder putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        return this;
    }


    public SpBuilder putInt(String key, int value) {
        editor.putInt(key, value);
        return this;
    }


    public String getString(String key) {
        return currentSp.getString(key, "no value");
    }


    public int getInt(String key) {
        return currentSp.getInt(key, 0);
    }

    public long getLong(String key) {
        return currentSp.getLong(key, 0L);
    }

    public float getFloat(String key) {
        return currentSp.getFloat(key, 0.0f);
    }


    public boolean getBoolean(String key) {
        return currentSp.getBoolean(key, false);
    }

    public SpBuilder remove(String key) {
        editor.remove(key);
        return this;
    }

    public SpBuilder clear(){
        editor.clear();
        return this;
    }
}
