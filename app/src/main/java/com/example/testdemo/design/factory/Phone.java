package com.example.testdemo.design.factory;

import android.util.Log;

public class Phone implements Product {
    private String TAG = "Phone";

    public void print() {
        System.out.println(TAG);
    }

    @Override
    public void createProduct() {

    }
}
