package org.example.basic.v2_线程组和线程优先级;

import java.util.stream.IntStream;

/**
 * * 2.线程的优先级
 * Java中线程优先级可以指定，范围是1~10。
 * Java只是给操作系统一个优先级的参考值，线程最终在操作系统的优先级是多少还是由操作系统决定。
 * Java默认的线程优先级为5，线程的执行顺序由调度程序来决定，线程的优先级会在线程被调用之前设定。
 * 通常情况下，高优先级的线程将会比低优先级的线程有更高的几率得到执行。
 * 守护线程默认的优先级比较低
 *
 * @author yayee
 * @version 2022/7/1
 */
public class Demo2 {
    public static class T1 extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println(String.format("当前执行的线程是：%s，优先级：%d",
                    Thread.currentThread().getName(),
                    Thread.currentThread().getPriority()));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread();
        System.out.println("我是默认线程优先级：" + a.getPriority());
        Thread b = new Thread();
        b.setPriority(10);
        System.out.println("我是设置过的线程优先级：" + b.getPriority());
        Thread.sleep(1000);

        /*
          当线程和线程组的优先级不一致的时候将会怎样呢？
          如果某个线程优先级大于线程所在线程组的最大优先级，那么该线程的优先级将会失效，取而代之的是线程组的最大优先级
         */
        ThreadGroup threadGroup = new ThreadGroup("t1");
        threadGroup.setMaxPriority(6);
        Thread thread = new Thread(threadGroup, "thread");
        thread.setPriority(9);
        System.out.println("我是线程组的优先级" + threadGroup.getMaxPriority());
        System.out.println("我是线程的优先级" + thread.getPriority());
        Thread.sleep(1000);

        /*
          Java中的优先级来说不是特别的可靠，Java程序中对线程所设置的优先级只是给操作系统一个建议，操作系统不一定会采纳。
          而真正的调用顺序，是由操作系统的线程调度算法决定的。
         */
        IntStream.range(1, 10).forEach(i -> {
            Thread threadPri = new Thread(new T1());
            threadPri.setPriority(i);
            threadPri.start();
        });
    }
    /*
      Java提供一个线程调度器来监视和控制处于RUNNABLE状态的线程。
      线程的调度策略采用抢占式，优先级高的线程比优先级低的线程会有更大的几率优先执行。
      在优先级相同的情况下，按照“先到先得”的原则。
      每个Java程序都有一个默认的主线程，就是通过JVM启动的第一个线程main线程。
     */
}
