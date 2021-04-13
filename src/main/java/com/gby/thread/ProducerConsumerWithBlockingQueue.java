package com.gby.thread;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerWithBlockingQueue {
    private BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

    private class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    int i = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + "：生产" + i);
                    blockingQueue.put(i);
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
                    int i = blockingQueue.take();
                    System.out.println(Thread.currentThread().getName() + "：消费" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerWithBlockingQueue producerConsumerWithBlockingQueue = new ProducerConsumerWithBlockingQueue();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 4; i++) {
            executorService.submit(producerConsumerWithBlockingQueue.new Producer());
        }
        for (int i = 0; i < 2; i++) {
            executorService.submit(producerConsumerWithBlockingQueue.new Consumer());
        }
    }
}
