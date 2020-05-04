package com.example.testdemo.design.factory;

public class Test {
    public static void main(String[] ss){
        //简单工厂模式
        Aproduct ap= (Aproduct) ProductFactory.createProduct(ProductFactory.P_01);
        Bproduct bp= (Bproduct) ProductFactory.createProduct(ProductFactory.P_02);

        //工厂方法模式
//        Aproduct apt= (Aproduct) new AFactory().createdProduct();
//        Bproduct bpt= (Bproduct) new BFactory().createdProduct();
    }

}
