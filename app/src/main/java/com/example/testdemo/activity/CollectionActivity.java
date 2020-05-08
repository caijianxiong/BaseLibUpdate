package com.example.testdemo.activity;

import com.example.testdemo.R;
import com.example.testdemo.been.Data;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cjx.baselib.BaseActivity;

/**
 * 集合类别
 * collection
 * 接口
 * 1,map
 * 2,set
 * 3,Queue
 * 4,List
 */


public class CollectionActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView() {

        throw new NullPointerException("**********there is nullPointerException");

    }

    @Override
    public void initData() {



//        HashMap与HashTable  ，ConcurrentHashMap
        /**
         * 1,都继承Map
         * 2，HashMap非synchronized，线程不安全，但可以接受K-V为null
         * 3，迭代器不同HashMap-Iterator，HashTable-enumerator,区别是不是fail-fast
         * 4，由于HashTable是线程安全的也是synchronized，所以在单线程环境下它比HashMap要慢。如果你不需要同步，只需要单一线程，那么使用HashMap性能要好过HashTable。
         * 5，HashMap不能保证随着时间的推移Map中的元素次序是不变的。
         * 我们能否让HashMap同步？
         * HashMap可以通过下面的语句进行同步：
         * Map m = Collections.synchronizeMap(hashMap);
         */

        /**
         * map set元素均不能重复
         */


        /**
         * HashMap
         */
        //HashMap存值是有序但（不能保证随着时间推移，元素中次序不变） ,线程不安全，单线程存取速度快
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            hashMap.put("" + i, "value:" + i);
        }
        hashMap.put("1", "newValue:" + 1);
        listMap(hashMap);
        //HashMap 存取值得原理：当我们给put()方法传递键和值时，我们先对键调用hashCode()方法，返回的hashCode用于找到bucket位置来储存Entry对象，同理取对象


        /**
         * Hashtable
         */
        //HashTable是无序的，线程安全
        Hashtable<String, String> hashTable = new Hashtable<>();
        for (int i = 0; i < 10; i++) {
            hashTable.put("" + i, "value:" + i);
        }
        hashTable.put("1", "newValue:" + 1);

//        hashTable.put("11", null);
        listTable(hashTable);


        /**
         * ConcurrentHashMap
         */
        //ConcurrentHashMap 线程安全且高效的HashMap
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            concurrentHashMap.put("" + i, "value:" + i);
        }
        concurrentHashMap.put("1", "newValue:" + 1);//是否能重复
        listConcurrentHashMap(concurrentHashMap);


        /**
         * HashSet
         */
        //1,HashSet  实现set接口，仅仅用来存储对象，，通过重写对象的hashCode和equals方法判断两个对象是否相等
        //2,没有重写equals和hashCode，，可以存入相同的对象
        //3，HashSet存储的原理，还是用hashMap存储数据的----线程不安全
        HashSet<Data> strings = new HashSet<>();
        Data data = new Data("2019", 3, false, new Data.People("caicai", 25));
        Data data02 = new Data("2019", 3, false, new Data.People("caicai", 25));
        strings.add(data);
        strings.add(data02);
        listHashSet(strings);

        /**
         * ArrayList
         */
        //值往数组内存，，扩容1.5倍  failFast机制  允许存null  线程不安全
        ArrayList<String> arrayList=new ArrayList<>();

        LinkedList<String> linkedList=new LinkedList<>();

//        Iterator<String> it=linkedList.iterator();
//        while (it.hasNext()){
//            it.next()
//        }

//        BlockingQueue实现生产者消费者




    }

    private void listHashSet(HashSet<Data> set) {
        Iterator<Data> it = set.iterator();
        while (it.hasNext()) {
        }

    }

    private void listConcurrentHashMap(ConcurrentHashMap<String, String> concurrentHashMap) {
        listMap(concurrentHashMap);
    }


    private void listMap(Map<String, String> map) {
        //迭代器遍历
        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            String key = entries.next().getKey();
        }

        //
        Set<Map.Entry<String, String>> entrySet = map.entrySet();//把这个set取出来
        for (Map.Entry<String, String> entry : entrySet) {
        }


        Set<String> stringSet = map.keySet();
        for (String s : stringSet) {
        }

    }

    private void listTable(Hashtable<String, String> map) {
        listMap(map);

        Enumeration<String> en = map.keys();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
        }

        Enumeration<String> elements = map.elements();
        while (elements.hasMoreElements()) {
            String value = elements.nextElement();
        }
    }

}
