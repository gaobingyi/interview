package com.gby.pattern;

public class SingletonDemo {
    private static volatile SingletonDemo instance;

    private SingletonDemo() {

    }

    public SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        SingletonDemo singletonDemo = new SingletonDemo();
        System.out.println(singletonDemo.getInstance() == singletonDemo.getInstance());
    }
}
