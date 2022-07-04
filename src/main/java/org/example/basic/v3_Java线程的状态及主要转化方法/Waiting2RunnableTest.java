package org.example.basic.v3_Java线程的状态及主要转化方法;

import org.junit.jupiter.api.Test;

/**
 * * 2.WAITING状态与RUNNABLE状态的转换
 * 我们知道有3个方法可以使线程从RUNNABLE状态转为WAITING状态
 * <p>
 * Object.wait()
 * 调用wait()方法前线程必须持有对象的锁。
 * 线程调用wait()方法时，会释放当前的锁，直到有其他线程调用notify()/notifyAll()方法唤醒等待锁的线程。
 * 需要注意的是，其他线程调用notify()方法只会唤醒单个等待锁的线程，如有有多个线程都在等待这个锁的话不一定会唤醒到之前调用wait()方法的线程。
 * 同样，调用notifyAll()方法唤醒所有等待锁的线程之后，也不一定会马上把时间片分给刚才放弃锁的那个线程，具体要看系统的调度。
 * <p>
 * Thread.join()
 * 调用join()方法不会释放锁，会一直等待当前线程执行完毕（转换为TERMINATED状态）。
 *
 * @author yayee
 * @version 2022/7/1
 */
public class Waiting2RunnableTest {
    @Test
    public void blockedTest() throws InterruptedException {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "a");
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "b");

        a.start();
        a.join();
        b.start();
        System.out.println(a.getName() + ":" + a.getState()); // 输出TERMINATED
        System.out.println(b.getName() + ":" + b.getState());
    }

    // 同步方法争夺锁
    private synchronized void testMethod() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /*
      要是没有调用join方法，main线程不管a线程是否执行完毕都会继续往下走。
      a线程启动之后马上调用了join方法，这里main线程就会等到a线程执行完毕，所以这里a线程打印的状态固定是TERMIATED。
      至于b线程的状态，有可能打印RUNNABLE（尚未进入同步方法），也有可能打印TIMED_WAITING（进入了同步方法）。
     */
}
