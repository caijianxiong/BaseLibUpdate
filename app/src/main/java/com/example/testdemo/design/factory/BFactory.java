package com.example.testdemo.design.factory;

public class BFactory implements Factory {
    @Override
    public Product createdProduct() {
        return new Bproduct();
    }
}
