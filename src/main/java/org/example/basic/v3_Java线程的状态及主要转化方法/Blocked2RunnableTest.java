package org.example.basic.v3_Java线程的状态及主要转化方法;

import org.junit.jupiter.api.Test;

/**
 * * 1.BLOCKED与RUNNABLE状态的转换
 * 处于BLOCKED状态的线程是因为在等待锁的释放。
 * 假如这里有两个线程a和b，a线程提前获得了锁并且暂未释放锁，此时b就处于BLOCKED状态。
 *
 * @author yayee
 * @version 2022/7/1
 */
public class Blocked2RunnableTest {
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
        // Thread.sleep(1000L); // (2)需要注意这里main线程休眠了1000毫秒，而testMethod()里休眠了2000毫秒
        b.start();
        System.out.println(a.getName() + ":" + a.getState()); // 输出？
        System.out.println(b.getName() + ":" + b.getState()); // 输出？
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
      初看之下，大家可能会觉得线程a会先调用同步方法，同步方法内又调用了Thread.sleep()方法，
      必然会输出TIMED_WAITING，而线程b因为等待线程a释放锁所以必然会输出BLOCKED。
      其实不然，有两点需要值得大家注意，一是在测试方法blockedTest()内还有一个main线程，
      二是启动线程后执行run方法还是需要消耗一定时间的。不打断点的情况下，上面代码中都应该输出RUNNABLE。
      测试方法的main线程只保证了a，b两个线程调用start()方法（转化为RUNNABLE状态），
      还没等两个线程真正开始争夺锁，就已经打印此时两个线程的状态（RUNNABLE）了。
      a:RUNNABLE
      b:RUNNABLE
     */
    /*
      (2)在这个例子中，由于main线程休眠，所以线程a的run()方法跟着执行，线程b再接着执行。
      在线程a执行run()调用testMethod()之后，线程a休眠了2000ms（注意这里是没有释放锁的），
      main线程休眠完毕，接着b线程执行的时候是争夺不到锁的，所以这里输出：
      a:TIMED_WAITING
      b:BLOCKED
     */
}


