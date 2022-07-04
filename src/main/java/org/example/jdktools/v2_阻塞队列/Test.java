package org.example.jdktools.v2_阻塞队列;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * BlockingQueue一般用于生产者-消费者模式，
 * 生产者是往队列里添加元素的线程，消费者是从队列里拿元素的线程。
 * BlockingQueue就是存放元素的容器。
 *
 * @author yayee
 * @version 2022/7/2
 */
public class Test {
    private int queueSize = 10;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(queueSize);

    /**
     * 注意
     * put和tack操作都需要先获取锁，没有获取到锁的线程会被挡在第一道大门之外自旋拿锁，直到获取到锁。
     * 就算拿到锁了之后，也不一定会顺利进行put/take操作，需要判断队列是否可用（是否满/空），如果不可用，则会被阻塞，并释放锁。
     * 在第2点被阻塞的线程会被唤醒，但是在唤醒之后，依然需要拿到锁才能继续往下执行，
     * 否则，自旋拿锁，拿到锁了再while判断队列是否可用（这也是为什么不用if判断，而使用while判断的原因）。
     */
    public static void main(String[] args) {
        Test test = new Test();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();

        producer.start();
        consumer.start();
    }

    /**
     * take操作和put操作的流程是类似的，总结一下take操作的流程：
     * 所有执行take操作的线程竞争lock锁，拿到了lock锁的线程进入下一步，没有拿到lock锁的线程自旋竞争锁。
     * 判断阻塞队列是否为空，如果是空，则调用await方法阻塞这个线程，并标记为notEmpty（消费者）线程，同时释放lock锁,等待被生产者线程唤醒。
     * 如果没有空，则调用dequeue方法。注意这一步的线程还有一种情况是第二步中阻塞的线程被唤醒且又拿到了lock锁的线程。
     * 唤醒一个标记为notFull（生产者）的线程。
     */
    class Consumer extends Thread {

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (true) {
                try {
                    queue.take();
                    System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 总结put的流程：
     * 所有执行put操作的线程竞争lock锁，拿到了lock锁的线程进入下一步，没有拿到lock锁的线程自旋竞争锁。
     * 判断阻塞队列是否满了，如果满了，则调用await方法阻塞这个线程，并标记为notFull（生产者）线程，同时释放lock锁,等待被消费者线程唤醒。
     * 如果没有满，则调用enqueue方法将元素put进阻塞队列。注意这一步的线程还有一种情况是第二步中阻塞的线程被唤醒且又拿到了lock锁的线程。
     * 唤醒一个标记为notEmpty（消费者）的线程。
     */
    class Producer extends Thread {

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while (true) {
                try {
                    queue.put(1);
                    System.out.println("向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*
      注意，这个例子中的输出结果看起来可能有问题，比如有几行在插入一个元素之后，队列的剩余空间不变。
      这是由于System.out.println语句没有锁。
      考虑到这样的情况：线程1在执行完put/take操作后立即失去CPU时间片，然后切换到线程2执行put/take操作，
      执行完毕后回到线程1的System.out.println语句并输出，发现这个时候阻塞队列的size已经被线程2改变了，
      所以这个时候输出的size并不是当时线程1执行完put/take操作之后阻塞队列的size，
      但可以确保的是size不会超过10个。实际上使用阻塞队列是没有问题的。
     */
}
