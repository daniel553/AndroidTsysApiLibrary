package com.ievolutioned.tsysapilibrary;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.cardservices.AuthService;
import com.ievolutioned.tsysapilibrary.transit.cardservices.SaleService;
import com.ievolutioned.tsysapilibrary.transit.cardservices.TipAdjustmentService;
import com.ievolutioned.tsysapilibrary.transit.cardservices.VoidService;
import com.ievolutioned.tsysapilibrary.transit.model.Auth;
import com.ievolutioned.tsysapilibrary.transit.model.Sale;
import com.ievolutioned.tsysapilibrary.transit.model.TipAdjustment;
import com.ievolutioned.tsysapilibrary.transit.model.Void;
import com.ievolutioned.tsysapilibrary.util.AppUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

/**
 * TsysApiLibrary class. Manages the main instances for library.
 * <p>
 * Created by Daniel on 26/12/2016.
 * </p>
 */

public class TsysApiLibrary {
    private static final String TAG = TsysApiLibrary.class.getName();

    /**
     * Builds and executes the Authorization call
     *
     * @param auth     - {@link Auth} auth object
     * @param callback - {@link TransitServiceCallback} callback
     * @throws NullPointerException
     */
    public static void doAuthorization(@NonNull Auth auth, @NonNull TransitServiceCallback callback)
            throws NullPointerException {
        AuthService authService = AuthService.getInstance();
        authService.setCallback(callback);
        authService.setAuth(auth);
        authService.execute();
    }

    /**
     * Builds and executes the Sale service call
     *
     * @param sale     - {@link Sale} object
     * @param callback - {@link TransitServiceCallback} callback
     * @throws NullPointerException
     */
    public static void doSale(@NonNull Sale sale, @NonNull TransitServiceCallback callback)
            throws NullPointerException {
        SaleService saleService = SaleService.getInstance();
        saleService.setCallback(callback);
        saleService.setSale(sale);
        saleService.execute();
    }

    /**
     * Builds and executes the TipAdjustment service call
     *
     * @param tipAdjustment - {@link TipAdjustment} object
     * @param callback      - {@link TransitServiceCallback} callback
     * @throws NullPointerException
     */
    public static void doTipAdjustment(@NonNull TipAdjustment tipAdjustment,
                                       @NonNull TransitServiceCallback callback)
            throws NullPointerException {
        TipAdjustmentService tipAdjustmentService = TipAdjustmentService.getInstance();
        tipAdjustmentService.setCallback(callback);
        tipAdjustmentService.setTipAdjustment(tipAdjustment);
        tipAdjustmentService.execute();
    }

    /**
     * Builds an executes the Void service call
     *
     * @param voidObject - {@link Void} object
     * @param callback   - {@link TransitServiceCallback} callback
     * @throws NullPointerException
     */
    public static void doVoid(@NonNull Void voidObject, @NonNull TransitServiceCallback callback)
            throws NullPointerException {
        VoidService voidService = VoidService.getInstance();
        voidService.setCallback(callback);
        voidService.setVoidObject(voidObject);
        voidService.execute();
    }


    /**
     * Gets the Tsys device identifier by metatag data.
     *
     * @param context - {@link Context} app context.
     * @return the device id, null if not exists.
     */
    public static String getTsysDeviceId(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            return bundle.getString(AppUtil.META_DATA_DEVICE_ID, null);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Gets the Tsys transaction key by metatag data.
     *
     * @param context - {@link Context} app context.
     * @return the transaction key, null if not exists.
     */
    public static String getTsysTransactionKey(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            return bundle.getString(AppUtil.META_DATA_TRANSACTION_KEY, null);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }
}
