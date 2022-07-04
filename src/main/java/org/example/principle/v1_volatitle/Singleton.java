package org.example.principle.v1_volatitle;

/**
 * * 2.单例模式
 * volatile禁止重排序，“双重锁检查”
 *
 * @author yayee
 * @version 2022/7/2
 */
public class Singleton {
    private static volatile Singleton instance; // 不使用volatile关键字，是可能会发生错误的

    // 双重锁检验
    public static Singleton getInstance() {
        if (instance == null) { // 第7行
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton(); // 第10行
                }
            }
        }
        return instance;
    }
}
