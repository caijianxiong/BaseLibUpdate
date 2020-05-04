package com.example.testdemo.design.factory;

/**
 * 工厂方法模式，优点，解耦，只需要实现这个类，就可以创建生成不同产品的工厂，，缺点：额外创建的类太多
 */
public interface Factory {

    Product createdProduct();

}
