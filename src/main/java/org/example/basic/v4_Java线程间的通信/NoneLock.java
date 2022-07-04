package org.example.basic.v4_Java线程间的通信;

/**
 * * 1.无锁
 *
 * @author yayee
 * @version 2022/7/1
 */
public class NoneLock {

    // static Integer k = 0;

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                // k++;
                System.out.println("Thread A " + i);
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                // k++;
                System.out.println("Thread B " + i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
        // Thread.sleep(500L);
        // System.out.println("k = " + k);
    }
}

