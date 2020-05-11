package com.example.testdemo.design.principle;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 里式替换原则
 */
public class Lsp {
    public static void main(String[] aaa ){
        Son son=new Son();
        son.doThings(new TreeMap());

    }


    static class Father{

        public Collection doThings(Map map){
            System.out.println("父类方法被调用");
            return  map.values();
        }

    }

    static class Son extends Father{
        @Override
        public Collection doThings(Map map) {
            return super.doThings(map);
        }

        public Collection doThings(HashMap map){
            System.out.println("子类方法被调用");
            return  map.values();
        }
    }

}
