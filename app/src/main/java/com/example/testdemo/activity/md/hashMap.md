
*特性
1，HashMap存值是有序但（不能保证随着时间推移，元素中次序不变） ,线程不安全，单线程存取速度快
2，可以存null值
*问题
1,HashMap存取值得原理，
  答：put 通过Key的HashCode方法得到HashCode码，遭到bucket位置来存储entry<K,V>对象，
  get 同理，根据key的hashCode码，找到bucket位置的对象取出来
2，Hash碰撞？
  答：当put ，get时如果两个key的HashCode值相同，bucket相同，就会产生hash碰撞，，解决办法，
  重写key 的HashCode和equals方法----找到bucket位置后会调用keys.equals方法取找到链表中正确的位置
3，key值为什么推荐用Interger，String
   答：前提，key的值得hashcode一定得是唯一的，，减少hash碰撞的概率，
   String的值是final的，不可变的，而且已经重写好了equals和HashCode方法，
   可以用任何对象作为KEY，只要正确的重写了equals和HashCode方法，并且这个对象作为key 后不可改变

   hashMap   hashTable的区别
   1，两者都实现了map接口，底层实现均是数组加列表
   2，hashMap线程不安全，，存值KV可以为null----判断某个值是否存在  contain（key）fail-fast迭代器多线程会跑出异常
   3，hashTable线程安全存值取值都可以为null，迭代器是fail-safe
   4，
