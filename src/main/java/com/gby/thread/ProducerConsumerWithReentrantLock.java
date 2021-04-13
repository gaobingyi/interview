package com.gby.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWithReentrantLock {
    private final int maxSize = 10;
    private Queue<Integer> queue = new LinkedList<>();
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (queue.size() >= maxSize) {
                        System.out.println(Thread.currentThread().getName() + "：队列已满");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    condition.signalAll();
                    int i = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + "：生产" + i);
                    queue.offer(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (queue.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + "：队列已空");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    condition.signalAll();
                    int i = queue.poll();
                    System.out.println(Thread.currentThread().getName() + "：消费" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerWithReentrantLock producerConsumerWithReentrantLock = new ProducerConsumerWithReentrantLock();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            executorService.submit(producerConsumerWithReentrantLock.new Producer());
        }
        for (int i = 0; i < 4; i++) {
            executorService.submit(producerConsumerWithReentrantLock.new Consumer());
        }
    }
}
