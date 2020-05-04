package com.example.testdemo.design.factory;

import android.util.Log;

public class PC implements Product{

    private String TAG = "PC";

    public void print() {
        System.out.println(TAG);
    }

    @Override
    public void createProduct() {

    }
}
