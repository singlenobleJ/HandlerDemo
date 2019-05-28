package com.example.handlerlib;

/**
 * @author: lilinjie
 * @date: 2019-05-27 13:18
 * @description:
 */
public class Handler {

    private final MessageQueue mMessageQueue;

    public Handler() {
        Looper looper = Looper.myLooper();
        mMessageQueue = looper.mMessageQueue;
    }

    /**
     * send message
     *
     * @param msg
     */
    public void sendMessage(Message msg) {
        msg.target = this;
        mMessageQueue.enqueueMessage(msg);

    }

    /**
     * handle message
     *
     * @param msg
     */
    public void handleMessage(Message msg) {
    }
}
