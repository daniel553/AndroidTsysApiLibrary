package com.ievolutioned.tsysapilibrary.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Application utility class. Contains main configuration in code behind.
 * <p>
 * Created by Daniel on 08/12/2016.
 */

public class AppUtil {
    public static final String TAG = AppUtil.class.getName();
    public static final boolean isDebug = true;

    public static final String META_DATA_DEVICE_ID = "TSYS_DEVICE_ID";
    public static final String META_DATA_TRANSACTION_KEY = "TSYS_DEVICE_ID";

    private static final String DEBUG_TSYS_DEVICE_ID = "88300000228401";
    private static final String DEBUG_TSYS_TRANSACTION_KEY = "1SN6NMT7MI3XQ8SSJSL592DAHNVGCQC0";

    public static String getTsysDeviceId(Context context) {
        if (isDebug)
            return DEBUG_TSYS_DEVICE_ID;

        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            return bundle.getString(META_DATA_DEVICE_ID, null);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public static String getTsysTransactionKey(Context context) {
        if (isDebug)
            return DEBUG_TSYS_TRANSACTION_KEY;

        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            return bundle.getString(META_DATA_TRANSACTION_KEY, null);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }
}
