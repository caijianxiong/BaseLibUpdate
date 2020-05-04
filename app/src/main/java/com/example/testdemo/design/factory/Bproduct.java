package com.example.testdemo.design.factory;

public class Bproduct implements Product {
    @Override
    public void createProduct() {
        System.out.println("B---create");
    }
}
