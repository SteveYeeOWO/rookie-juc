package org.example.basic.v1_Java多线程入门类和接口;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Callable、Future与FutureTask
 * * 1.拥有返回值
 * 我们使用Runnable和Thread来创建一个新的线程。但是它们有一个弊端，就是run方法是没有返回值的
 * 有时候我们希望开启一个线程去执行一个任务，并且这个任务执行完成后有一个返回值。
 * JDK提供了Callable接口与Future类为我们解决这个问题。
 * * 2.能够取消执行
 * Future接口中cancel方法，试图取消一个线程的执行。注意是试图取消，并不一定能取消成功。
 * 所以有时候，为了让任务有能够取消的功能，就使用Callable来代替Runnable。
 * 如果为了可取消性而使用 Future但又不提供可用的结果，则可以声明 Future<?>形式类型、并返回 null作为底层任务的结果。
 *
 * @author yayee
 * @version 2022/7/1
 */
public class Demo2 {
    /**
     * 自定义Callable
     * 那一般是怎么使用Callable的呢？Callable一般是配合线程池工具ExecutorService来使用的。
     * ExecutorService可以使用submit方法来让一个Callable接口执行。
     * 它会返回一个Future，我们后续的程序可以通过这个Future的get方法得到结果。
     */
    public static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            // 模拟计算需要一秒
            Thread.sleep(1000);
            return 2;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 使用
        ExecutorService executor1 = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = executor1.submit(task);
        // 注意调用get方法会阻塞当前线程，直到得到结果。
        // 所以实际编码中建议使用可以设置超时时间的重载get方法。
        System.out.println("case1: " + result.get());

        // 使用 Future接口的实现类 FutureTask
        ExecutorService executor2 = Executors.newCachedThreadPool();
        FutureTask<Integer> futureTask = new FutureTask<>(new Task());
        executor2.submit(futureTask);
        System.out.println("case2: " + futureTask.get());
        /*
          使用上与第一个Demo有一点小的区别。
          首先，调用submit方法是没有返回值的。
          这里实际上是调用的submit(Runnable task)方法，而上面的Demo，调用的是submit(Callable<T> task)方法。
          然后，这里是使用FutureTask直接取get取值，而上面的Demo是通过submit方法返回的Future去取值。
          在很多高并发的环境下，有可能Callable和FutureTask会创建多次。
          FutureTask能够在高并发环境下确保任务只执行一次。
         */
    }
}
