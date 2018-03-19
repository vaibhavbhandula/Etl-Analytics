package com.noonEdu.nAnalytics.commons;

import android.util.Log;

import com.noonEdu.nAnalytics.BuildConfig;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public class LogUtils {
    private static final boolean isLogEnabled = BuildConfig.DEBUG;

    public static void printLog(String tag, String message) {
        if (isLogEnabled) {
            Log.v(tag, message);

        }
    }

    public static void printStackTrace(Throwable throwable) {
        if (isLogEnabled) {
            throwable.printStackTrace();
        }
    }
}
