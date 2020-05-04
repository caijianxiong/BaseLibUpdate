package com.example.testdemo.design.factory;

public class HuaweiFactory implements AbstractFactory {
    @Override
    public Product createPhone() {
        return new Phone();
    }

    @Override
    public Product createPC() {
        return new PC();
    }
}
