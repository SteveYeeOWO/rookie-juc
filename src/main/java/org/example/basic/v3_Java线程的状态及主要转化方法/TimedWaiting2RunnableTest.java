package org.example.basic.v3_Java线程的状态及主要转化方法;

import org.junit.jupiter.api.Test;

/**
 * * 3.TIMED_WAITING与RUNNABLE状态转换
 * TIMED_WAITING与WAITING状态类似，只是TIMED_WAITING状态等待的时间是指定的。
 * <p>
 * Thread.sleep(long)
 * 使当前线程睡眠指定时间。需要注意这里的“睡眠”只是暂时使线程停止执行，并不会释放锁。时间到后，线程会重新进入RUNNABLE状态。
 * <p>
 * Object.wait(long)
 * wait(long)方法使线程进入TIMED_WAITING状态。这里的wait(long)方法与无参方法wait()相同的地方是，都可以通过其他线程调用notify()或notifyAll()方法来唤醒。
 * 不同的地方是，有参方法wait(long)就算其他线程不来唤醒它，经过指定时间long之后它会自动唤醒，拥有去争夺锁的资格。
 * <p>
 * Thread.join(long)
 * join(long)使当前线程执行指定时间，并且使线程进入TIMED_WAITING状态。
 * <p>
 * 这里需要强调一下：sleep方法是不会释放当前的锁的，而wait方法会。这也是最常见的一个多线程面试题。
 * 它们还有这些区别：
 * wait可以指定时间，也可以不指定；而sleep必须指定时间。
 * wait释放cpu资源，同时释放锁；sleep释放cpu资源，但是不释放锁，所以易死锁。
 * wait必须放在同步块或同步方法中，而sleep可以再任意位置
 *
 * @author yayee
 * @version 2022/7/1
 */
public class TimedWaiting2RunnableTest {
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
        a.join(1000L);
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
      这里调用a.join(1000L)，因为是指定了具体a线程执行的时间的，并且执行时间是小于a线程sleep的时间，所以a线程状态输出TIMED_WAITING。
      b线程状态仍然不固定（RUNNABLE或BLOCKED）。
     */
}


