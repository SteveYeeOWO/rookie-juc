package org.example.basic.v4_Java线程间的通信;

/**
 * * 2.对象锁
 * 使用“对象锁“实现线程A先执行完之后，再执行线程B
 * 这里声明了一个名字为lock的对象锁。
 * 我们在ThreadA和ThreadB内需要同步的代码块里，都是用synchronized关键字加上了同一个对象锁lock。
 * 上文我们说到了，根据线程和锁的关系，同一时间只有一个线程持有一个锁，
 * 那么线程B就会等线程A执行完成后释放lock，线程B才能获得锁lock。
 *
 * @author yayee
 * @version 2022/7/1
 */
public class ObjectLock {
    private static Object lock = new Object();

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread A " + i);
                }
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread B " + i);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        Thread.sleep(10);
        new Thread(new ThreadB()).start();
    }
    /*
      这里在主线程里使用sleep方法睡眠了10毫秒，是为了防止线程B先得到锁。因为如果同时start，线程A和线程B都是出于就绪状态，操作系统可能会先让B运行。
      这样就会先输出B的内容，然后B执行完成之后自动释放锁，线程A再执行。
     */
}
