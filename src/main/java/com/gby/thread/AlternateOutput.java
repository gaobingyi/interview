package com.gby.thread;

public class AlternateOutput {
    private int count = 1;
    private final int maxCount;
    private final int threadNum;
    private Object lock = new Object();

    public AlternateOutput(int maxCount, int threadNum) {
        this.maxCount = maxCount;
        this.threadNum = threadNum;
    }

    public void output(int i) {
        while (true) {
            synchronized (lock) {
                while (count <= maxCount && count % threadNum != (i + 1) % threadNum) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notifyAll();
                if (count > maxCount) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ": " + count++);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int maxCount = 30;
        int threadNum = 3;
        AlternateOutput alternateOutput = new AlternateOutput(maxCount, threadNum);
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            final int j = i;
            threads[i] = new Thread(() -> {
                alternateOutput.output(j);
            }, "Thread-" + (j + 1));
            threads[i].start();
        }
        for (int i = 0; i < threadNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ": Over!");
    }
}
