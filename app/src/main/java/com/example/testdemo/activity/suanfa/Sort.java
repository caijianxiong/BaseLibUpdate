package com.example.testdemo.activity.suanfa;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caicai
 * @create 2019/12/25
 * @Describe
 */
public class Sort {

    public static void main(String[] a) {

        int[] ints = new int[]{40, 3, 56, 1, 4, 6, 7, 0, 12, 9};

//        selectSort(ints);
//        bubblingSort(ints);
        insertSort(ints);


    }

    /**
     * 插入排序
     *
     * @param ints
     */
    private static void insertSort(int[] ints) {
        //小的往前插。从第二个开始往前对比

        for (int i = 1; i < ints.length; i++) {
            for (int j = i; j > 0; j--) {
                if (ints[j] < ints[j - 1]) {
                    replace(ints, j, j - 1);
                }
            }
            System.out.print("\n");
            for (int n = 0; n < ints.length; n++) {
                System.out.print(ints[n] + " ");
            }
        }


    }

    /**
     * 冒泡排序
     *
     * @param ints
     */
    private static void bubblingSort(int[] ints) {
        //每循环一次最大的值往后排（相邻的两个只比较，，大的往后换值），，每次index 0---n  0----n-1  0---1

        for (int i = 0; i < ints.length - 1; i++) {
            for (int j = 0; j < ints.length - 1 - i; j++) {
                if (ints[j] > ints[j + 1]) {
                    replace(ints, j, j + 1);
                }
            }

            System.out.print("\n");
            for (int n = 0; n < ints.length; n++) {
                System.out.print(ints[n] + " ");
            }
        }

//        for (int i = 0; i < ints.length; i++) {
//            System.out.print(ints[i] + " ");
//        }

    }

    /**
     * 选择排序（不稳定，，值相同会出问题）
     *
     * @param ints
     */
    private static void selectSort(int[] ints) {
        //每次循环选最小的直接跟第一位交换(根据index换值)  index  0-->n   1-->n  2-->n  n-1-->n
        for (int i = 0; i < ints.length - 1; i++) {
            int minPos = i;
            for (int j = i + 1; j < ints.length; j++) {//在比较数组其他的值，选最小的再移到第一位
                if (ints[minPos] > ints[j]) {
                    minPos = j;
                }
            }
//            System.out.print("minPos:" + minPos);
            //一次循环找到一个最小的，，跟第一个交换
            //最小值移到第一位
            replace(ints, i, minPos);

            System.out.print("\n");

            for (int n = 0; n < ints.length; n++) {
                System.out.print(ints[n] + " ");
            }
        }
    }

    private static void replace(int[] ints, int i0, int i1) {
        int temp = ints[i0];
        ints[i0] = ints[i1];
        ints[i1] = temp;
    }
}
