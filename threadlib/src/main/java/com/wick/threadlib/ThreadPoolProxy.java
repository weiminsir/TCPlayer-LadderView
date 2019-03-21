package com.wick.threadlib;

import android.util.TimeUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author:weiminzhang
 * @Created:2019/3/15
 */
public class ThreadPoolProxy {


    public ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;
    private int mMaximumPoolSize;


    /**
     * @param corePoolSize    核心池的大小
     * @param maximumPoolSize 最大线程数
     */
    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }

    //    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              RejectedExecutionHandler handler) {
//        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
    private void initThreadPoolExecutor() {

        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {

            synchronized (ThreadPoolProxy.class) {

                long keepAliveTime = 3000;

                TimeUnit unit = TimeUnit.MILLISECONDS;

                LinkedBlockingQueue queue = new LinkedBlockingQueue();

                ThreadFactory threadFactory = Executors.defaultThreadFactory();

                mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, keepAliveTime,
                        unit, queue, threadFactory);
            }
        }
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    public void removeTask(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);

    }


    public ExecutorService createCachedThreadPool() {
        return Executors.newCachedThreadPool();
    }


}
