package com.ievolutioned.tsysapilibrary.transit;

/**
 * {@link TransitServiceCallback} interface. Contains the main definitions for service callbacks
 * <p>
 * Created by Daniel on 08/12/2016.
 * </p>
 */

public interface TransitServiceCallback {
    /**
     * Success service call.
     *
     * @param msg      - success message.
     * @param response - {@link BaseResponse} response object.
     */
    void onSuccess(String msg, BaseResponse response);

    /**
     * Error service call or response.
     *
     * @param msg      - error message.
     * @param response - {@link BaseResponse} response object.
     */
    void onError(String msg, BaseResponse response);

    /**
     * Canceled task.
     */
    void onCancel();
}
