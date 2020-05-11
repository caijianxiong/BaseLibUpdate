package com.example.testdemo.design;

import android.util.Log;

import java.lang.reflect.Constructor;

public class Singletonfactory {
    private static String TAG="Singletonfactory";
    private static Singleton singleton;


    static {
        try {
            Class c=Class.forName(Singleton.class.getName());
            Constructor constructor=c.getDeclaredConstructor();
            constructor.setAccessible(true);
            singleton= (Singleton) constructor.newInstance();

        }catch (Exception e){
            Log.d(TAG, "static initializer: "+e.getMessage());
        }
    }

    public static Singleton getSingleton(){
        return singleton;
    }

}
