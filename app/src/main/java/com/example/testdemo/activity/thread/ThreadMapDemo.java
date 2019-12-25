package com.example.testdemo.activity.thread;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author caicai
 * @create 2019/12/23
 * @Describe
 */
public class ThreadMapDemo {
    private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();//高并发，，分段式锁，，效率更高
    private ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();//高并发并且排序
    private Hashtable<String, String> hashtable = new Hashtable<>();//支持并发



    private CopyOnWriteArrayList copyOnWriteArrayList=new CopyOnWriteArrayList();//每一次添加一个，，复制一次数组
    private HashMap<String, String> hashMap = new HashMap<>();
    private TreeMap<String, String> treeMap = new TreeMap<>();


    //BlockingQueue
}
