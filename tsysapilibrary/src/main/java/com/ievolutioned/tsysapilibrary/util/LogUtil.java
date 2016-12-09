package com.ievolutioned.tsysapilibrary.util;

import android.util.Log;

/**
 * Log utility class. Allows logger for a debug mode
 * <p>
 * Created by Daniel on 08/12/2016.
 */

public class LogUtil {

    /**
     * Debug output. See {@link Log} d
     *
     * @param tag - TAG
     * @param msg - a message
     */
    public static void d(String tag, String msg) {
        if (AppUtil.isDebug)
            Log.d(tag, msg);
    }

    /**
     * Error output. See {@link Log} e
     *
     * @param tag - TAG
     * @param msg - a message
     * @param e   - {@link Throwable} error
     */
    public static void e(final String tag, final String msg, final Throwable e) {
        if (AppUtil.isDebug)
            Log.e(tag, msg, e);
    }

    /**
     * Error output. See {@link Log} e
     *
     * @param tag - TAG
     * @param msg - a message
     */
    public static void e(final String tag, final String msg) {
        e(tag, msg, null);
    }

}
