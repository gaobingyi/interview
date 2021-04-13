package com.gby.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerWithSynchronized {
    private final int maxSize = 10;
    private Queue<Integer> queue = new LinkedList<>();

    private class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() >= maxSize) {
                        System.out.println(Thread.currentThread().getName() + "：队列已满");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.notifyAll();
                    int i = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + "：生产" + i);
                    queue.offer(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + "：队列已空");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.notifyAll();
                    int i = queue.poll();
                    System.out.println(Thread.currentThread().getName() + "：消费" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        ProducerConsumerWithSynchronized producerConsumerWithSynchronized = new ProducerConsumerWithSynchronized();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 4; i++) {
            executorService.submit(producerConsumerWithSynchronized.new Producer());
        }
        for (int i = 0; i < 2; i++) {
            executorService.submit(producerConsumerWithSynchronized.new Consumer());
        }
    }
}
