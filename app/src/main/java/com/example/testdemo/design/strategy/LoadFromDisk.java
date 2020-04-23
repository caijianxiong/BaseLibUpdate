package com.example.testdemo.design.strategy;


public class LoadFromDisk implements BaseStrategy{


    @Override
    public String getImgDrawable() {
        return "从本地加载图片" ;
    }
}
