package org.example.basic.v1_Java多线程入门类和接口;

/**
 * * 1 Thread类构造方法
 * 实际情况下，我们大多是直接调用下面两个构造方法：
 * Thread(Runnable target)
 * Thread(Runnable target, String name)
 * <p>
 * * 2 Thread类的几个常用方法
 * 这里介绍一下Thread类的几个常用的方法：
 * currentThread()：静态方法，返回对当前正在执行的线程对象的引用；
 * start()：开始执行线程的方法，java虚拟机会调用线程内的run()方法；
 * yield()：yield在英语里有放弃的意思，同样，这里的yield()指的是当前线程愿意让出对当前处理器的占用。这里需要注意的是，就算当前线程调用了yield()方法，程序在调度的时候，也还有可能继续运行这个线程的；
 * sleep()：静态方法，使当前线程睡眠一段时间；
 * join()：使当前线程等待另一个线程执行完毕之后再继续执行，内部调用的是Object类的wait方法实现的；
 * <p>
 * * 3 Thread类与Runnable接口的比较：
 * 实现一个自定义的线程类，可以有继承Thread类或者实现Runnable接口这两种方式，它们之间有什么优劣呢？
 * 由于Java“单继承，多实现”的特性，Runnable接口使用起来比Thread更灵活。
 * Runnable接口出现更符合面向对象，将线程单独进行对象的封装。
 * Runnable接口出现，降低了线程对象和线程任务的耦合性。
 * 如果使用线程时不需要使用Thread类的诸多方法，显然使用Runnable接口更为轻量。
 * 所以，我们通常优先使用“实现Runnable接口”这种方式来自定义线程类。
 *
 * @author yayee
 * @version 2022/7/1
 */
public class Demo1 {
    /**
     * 1.继承Thread类
     */
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread");
        }
    }

    /**
     * 2.实现Runnable接口
     */
    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("MyRunnable");
        }
    }

    public static void main(String[] args) {
        Thread myThread1 = new MyThread();
        myThread1.start();

        MyRunnable myRunnable1 = new MyRunnable();
        Thread myThread2 = new Thread(myRunnable1);
        myThread2.start();

        // Java 8 函数式编程，可以省略MyThread2类
        Thread myThread3 = new Thread(() -> {
            System.out.println("Java 8 匿名内部类");
        });
        myThread3.start();
    }
    /*
      注意要调用start()方法后，该线程才算启动！
      我们在程序里面调用了start()方法后，虚拟机会先为我们创建一个线程，然后等到这个线程第一次得到时间片时再调用run()方法。
      注意不可多次调用start()方法。在第一次调用start()方法后，再次调用start()方法会抛出异常。
     */
}
