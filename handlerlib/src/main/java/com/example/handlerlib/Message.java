package com.example.handlerlib;

/**
 * @author: lilinjie
 * @date: 2019-05-27 13:19
 * @description:
 */
public class Message {
    public Object obj;
    public Handler target;

    public Message(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Message{" +
                "obj=" + obj +
                '}';
    }
}
