package org.example.basic.v4_Java线程间的通信;

/**
 * * 6.join()
 * join()方法是Thread类的一个实例方法。
 * 它的作用是让当前线程陷入“等待”状态，等join的这个线程执行完成后，再继续执行当前线程。
 * 有时候，主线程创建并启动了子线程，如果子线程中需要进行大量的耗时运算，主线程往往将早于子线程结束之前结束。
 * 如果主线程想等待子线程执行完毕后，获得子线程中的处理完的某个数据，就要用到join方法了。
 *
 * @author yayee
 * @version 2022/7/1
 */
public class Join {
    static class ThreadA implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("我是子线程，我先睡一秒");
                Thread.sleep(1000);
                System.out.println("我是子线程，我睡完了一秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadA());
        thread.start();
        thread.join();
        System.out.println("如果不加join方法，我会先被打出来，加了就不一样了");
    }
    /*
      注意join()方法有两个重载方法，一个是join(long)， 一个是join(long, int)。
      实际上，通过源码你会发现，join()方法及其重载方法底层都是利用了wait(long)这个方法。
      对于join(long, int)，通过查看源码(JDK 1.8)发现，底层并没有精确到纳秒，而是对第二个参数做了简单的判断和处理。
     */
}
