package com.example.testdemo.design.factory;

import com.example.testdemo.design.Product;

public class Aproduct implements Product {
    @Override
    public void createProduct() {
        System.out.println("A---create");
    }
}
