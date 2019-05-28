package com.example.handlerlib;

/**
 * @author: lilinjie
 * @date: 2019-05-27 13:19
 * @description:
 */
public class Looper {

    /*package*/final MessageQueue mMessageQueue;
    private static ThreadLocal<Looper> mThreadLocalLooper = new ThreadLocal<>();

    private Looper() {
        mMessageQueue = new MessageQueue();
    }

    public static void prepare() {
        if (mThreadLocalLooper.get() != null) {
            throw new RuntimeException("only create one looper per thread!");
        }
        mThreadLocalLooper.set(new Looper());
    }

    public static Looper myLooper() {
        if (mThreadLocalLooper.get() == null) {
            throw new RuntimeException("must create looper first!");
        }
        return mThreadLocalLooper.get();
    }

    /**
     * loop message from MessageQueue
     */
    public static void loop() {
        Looper looper = Looper.myLooper();
        Message msg;
        for (; ; ) {
            msg = looper.mMessageQueue.next();
            if (msg == null || msg.target == null) {
                continue;
            }
            LogUtils.log("handler start handle message by call handleMessage() in " + Thread.currentThread().getName());
            msg.target.handleMessage(msg);
        }
    }
}
