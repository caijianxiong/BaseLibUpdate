package com.example.testdemo.design.factory;

/**
 * 抽象工厂，，，优点同一个工厂可以生成不同的商品，，工厂方法只能生成一类商品，，可以减少类的创建，缺点新增一类商品创建需要改动抽象工厂接口
 */
public interface AbstractFactory {

     Product createPhone();
     Product createPC();

}
