package com.example.handlerlib;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: lilinjie
 * @date: 2019-05-27 13:18
 * @description:
 */
public class MessageQueue {
    private static final int MESSAGE_POOL_SIZE = 50;//消息池大小
    private Message[] mMessages;//消息池
    private Lock mLock = new ReentrantLock();//lock
    private Condition mPutCondition = mLock.newCondition();//put condition
    private Condition mTakeCondition = mLock.newCondition();//take condition
    private int mPutIndex = 0;//存储的消息index
    private int mTakeIndex = 0;//取出的消息index
    private int mCount = 0;//消息池中的消息数量

    public MessageQueue() {
        mMessages = new Message[MESSAGE_POOL_SIZE];
    }

    /**
     * put message
     *
     * @param msg
     */
    public void enqueueMessage(Message msg) {
        try {
            mLock.lock();
            while (mCount >= mMessages.length) {
                //生产者等待
                LogUtils.log("message queue is full producer waiting...");
                mPutCondition.await();
            }
            LogUtils.log("message queue put " + Thread.currentThread().getName() + " one message.");
            mMessages[mPutIndex] = msg;
            mPutIndex++;
            if (mPutIndex >= mMessages.length) {
                mPutIndex = 0;
            }
            mCount++;
            //唤醒消费者
            mTakeCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }
    }

    /**
     * take message
     */
    public Message next() {
        Message msg = null;
        try {
            mLock.lock();
            while (mCount <= 0) {
                //消费者等待
                LogUtils.log("message queue is empty consumer waiting...");
                mTakeCondition.await();
            }
            LogUtils.log("message queue take " + Thread.currentThread().getName() + " one message.");
            msg = mMessages[mTakeIndex];
            mMessages[mTakeIndex] = null;
            mTakeIndex++;
            if (mTakeIndex >= mMessages.length) {
                mTakeIndex = 0;
            }
            mCount--;
            //通知生产者生产
            mPutCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }
        return msg;
    }
}
