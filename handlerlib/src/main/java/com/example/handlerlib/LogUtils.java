package com.example.handlerlib;

import android.util.Log;

/**
 * @author: lilinjie
 * @date: 2019-05-27 17:25
 * @description:
 */
public class LogUtils {
    private static final String TAG = "LogUtils";
    private static final boolean DEBUG = true;

    public static void log(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }

    }
}
