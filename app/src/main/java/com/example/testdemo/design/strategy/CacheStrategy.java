package com.example.testdemo.design.strategy;

/**
 * 策略模式
 * 例如，，加载一张图片
 * 定义去图片的策略
 */
public class CacheStrategy {

    public static void main(String[] a) {
        LoadFromDisk loadFromDisk = new LoadFromDisk();
        Strategy strategy = new Strategy();
        strategy.setStrategy(loadFromDisk);
        System.out.println(strategy.load());

    }

    static class Strategy {
        private BaseStrategy strategy;

        public void setStrategy(BaseStrategy s) {
            strategy = s;
        }


        public String load() {
            return strategy.getImgDrawable();
        }
    }


}
