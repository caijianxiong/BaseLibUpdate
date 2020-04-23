package com.example.testdemo.design.strategy;

public class LoadFromNet implements BaseStrategy {
    @Override
    public String getImgDrawable() {
        return "从网络加载图片";
    }
}
