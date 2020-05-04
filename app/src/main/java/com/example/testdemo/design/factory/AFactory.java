package com.example.testdemo.design.factory;

public class AFactory implements Factory {
    @Override
    public Product createdProduct() {
        return new Aproduct();
    }
}
