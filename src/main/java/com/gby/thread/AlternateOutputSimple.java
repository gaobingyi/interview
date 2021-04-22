package com.gby.thread;

public class AlternateOutputSimple {
    private static int n = 1;
    private static Object lock = new Object();

    private static void output(int i) throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (n <= 10 && n % 2 == i) {
                    lock.wait();
                }
                if (n > 10) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ": " + n++);
                lock.notify();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    output(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    output(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
