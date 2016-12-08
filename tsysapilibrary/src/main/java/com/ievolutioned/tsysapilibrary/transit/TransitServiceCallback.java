package com.ievolutioned.tsysapilibrary.transit;

/**
 * Created by Daniel on 08/12/2016.
 */

public interface TransitServiceCallback {
    void onSuccess(String msg, BaseResponse response);
    void onError(String msg, BaseResponse response);
    void onCancel();
}
