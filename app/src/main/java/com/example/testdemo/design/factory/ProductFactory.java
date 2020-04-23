package com.example.testdemo.design.factory;

import com.example.testdemo.design.Product;

/**
 * 工厂负责生产不同类型的对象，，外界根据传入不同的类型，取的产品，而不用在意生产的过程
 */
public class ProductFactory {

    public static final int P_01 = 10010;
    public static final int P_02 = 10011;
    public static final int P_03 = 10012;

    public static Product createProduct(int type) {
        Product product = null;

        switch (type) {

            case P_01:
                product = new Aproduct();
                break;
            case P_02:
                product = new Bproduct();
                break;
        }

        return product;
    }

}
