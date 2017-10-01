package com.coprorated.amizaar.myarchitecturedtest.util;

import android.util.Log;

/**
 * Created by amizaar on 18.07.2017.
 */

public class LogUtils {

    public static void logd(String logString) {
        Log.d(new Throwable().getStackTrace()[1].getClassName(), logString);
    }

    public static void loge(String logString) {
        Log.e(new Throwable().getStackTrace()[1].getClassName(), logString);
    }
}
