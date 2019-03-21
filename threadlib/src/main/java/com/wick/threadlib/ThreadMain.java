package com.wick.threadlib;

/**
 * @Description:
 * @Author:weiminzhang
 * @Created:2019/3/15
 */
public class ThreadMain {

    public void main() {
        ThreadPoolFactoryManager.getDownLoadVideoProxy().execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
