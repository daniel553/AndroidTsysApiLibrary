package com.ievolutioned.androidtsysapilibrary.example.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Daniel on 27/12/2016.
 */

public class ActivityUtil {
    public static void startActivity(Context context, Class<?> clazz) {
        Intent i = new Intent(context, clazz);
        context.startActivity(i);
    }
}
