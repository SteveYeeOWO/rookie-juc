package org.example.principle.v2_synchronized与锁;

/**
 * * 3.synchronized锁
 * 1)关键字在实例方法上，锁为当前实例
 * 2)关键字在静态方法上，锁为当前Class对象
 * 3)关键字在代码块上，锁为括号里面的对象
 * 我们这里介绍一下“临界区”的概念。所谓“临界区”，指的是某一块代码区域，它同一时刻只能由一个线程执行。
 * 在下面的例子中，如果synchronized关键字在方法上，那临界区就是整个方法内部。
 * 而如果是使用synchronized代码块，那临界区就指的是代码块内部的区域。
 *
 * @author yayee
 * @version 2022/7/2
 */
public class SynchronizedExample {
    // 1.关键字在实例方法上，锁为当前实例
    public synchronized void instanceLock() {
        // code
    }
    /*
      // 关键字在代码块上，锁为括号里面的对象，与1等价
      public void blockLock() {
        synchronized (this) {
          // code
        }
      }
     */

    // 2.关键字在静态方法上，锁为当前Class对象
    public static synchronized void classLock() {
        // code
    }
    /*
      // 关键字在代码块上，锁为括号里面的对象，与2等价
      public void blockLock() {
        synchronized (this.getClass()) {
          // code
        }
      }
     */

    // 3.关键字在代码块上，锁为括号里面的对象
    public void blockLock() {
        Object o = new Object();
        synchronized (o) {
            // code
        }
    }
}
