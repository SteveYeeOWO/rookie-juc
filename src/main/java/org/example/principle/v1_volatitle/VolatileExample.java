package org.example.principle.v1_volatitle;

import org.junit.jupiter.api.Test;

/**
 * * 1.volatile主要有以下两个功能：
 * 保证变量的内存可见性
 * 禁止volatile变量与普通变量重排序（JSR133提出，Java 5 开始才有这个“增强的volatile内存语义”）
 * <p>
 * 从volatile的内存语义上来看，volatile可以保证内存可见性且禁止重排序。
 * 在保证内存可见性这一点上，volatile有着与锁相同的内存语义，所以可以作为一个“轻量级”的锁来使用。
 * 但由于volatile仅仅保证对单个volatile变量的读/写具有原子性，而锁可以保证整个临界区代码的执行具有原子性。
 * 所以在功能上，锁比volatile更强大；在性能上，volatile更有优势。
 *
 * @author yayee
 * @version 2022/7/2
 */
public class VolatileExample {
    int a = 0;
    volatile boolean flag = false;

    public void writer() {
        a = 1; // step 1
        flag = true; // step 2
    }

    public void reader() {
        if (flag) { // step 3
            System.out.println("T" + a); // step 4
        } else {
            System.out.println("F" + a);
        }
    }
    /*
      所谓内存可见性，指的是当一个线程对volatile修饰的变量进行写操作（比如step 2）时，JMM会立即把该线程对应的本地内存中的共享变量的值刷新到主内存；当一个线程对volatile修饰的变量进行读操作（比如step 3）时，JMM会把立即该线程对应的本地内存置为无效，从主内存中读取共享变量的值。
      在这一点上，volatile与锁具有相同的内存效果，volatile变量的写和锁的释放具有相同的内存语义，volatile变量的读和锁的获取具有相同的内存语义。
     */

    @Test
    public void MyTest() {
        Thread t1 = new Thread(() -> {
            reader();
        });
        Thread t2 = new Thread(() -> {
            writer();
        });
        Thread t3 = new Thread(() -> {
            reader();
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
