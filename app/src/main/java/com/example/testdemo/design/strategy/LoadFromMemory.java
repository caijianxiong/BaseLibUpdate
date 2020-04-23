package com.example.testdemo.design.strategy;

public class LoadFromMemory implements BaseStrategy {
    @Override
    public String getImgDrawable() {
        return "从内存中加载图片";
    }
}
