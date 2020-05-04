package com.example.testdemo.design.factory;

public class Aproduct implements Product {
    @Override
    public void createProduct() {
        System.out.println("A---create");
    }
}
