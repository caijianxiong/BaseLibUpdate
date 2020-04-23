package com.example.testdemo.design.factory;

import com.example.testdemo.design.Product;

public class Bproduct implements Product {
    @Override
    public void createProduct() {
        System.out.println("B---create");
    }
}
