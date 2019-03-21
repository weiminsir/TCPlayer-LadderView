package com.wick.threadlib;

/**
 * @Description:
 * @Author:weiminzhang
 * @Created:2019/3/15
 */
public class ThreadPoolFactoryManager {

    private static ThreadPoolProxy mDownLoadVideoProxy;

    public static ThreadPoolProxy getDownLoadVideoProxy() {

        if (mDownLoadVideoProxy == null) {
            synchronized (ThreadPoolFactoryManager.class) {
                if (mDownLoadVideoProxy == null) {
                    mDownLoadVideoProxy = new ThreadPoolProxy(20,30);
                }
            }
        }
        return mDownLoadVideoProxy;

    }
}
