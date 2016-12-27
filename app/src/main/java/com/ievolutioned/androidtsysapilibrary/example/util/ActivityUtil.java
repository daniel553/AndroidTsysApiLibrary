package com.ievolutioned.androidtsysapilibrary.example.util;

import android.content.Context;
import android.content.Intent;

/**
 * Activity utilityu class
 * <p>
 * Created by Daniel on 27/12/2016.
 * </p>
 */
public class ActivityUtil {

    /**
     * Starts an activity
     *
     * @param context - {@link Context}
     * @param clazz   - activity
     */
    public static void startActivity(Context context, Class<?> clazz) {
        Intent i = new Intent(context, clazz);
        context.startActivity(i);
    }
}
