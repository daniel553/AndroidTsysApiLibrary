package com.ievolutioned.tsysapilibrary.transit;

import android.support.annotation.NonNull;

import com.ievolutioned.tsysapilibrary.util.JsonUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 08/12/2016.
 */
public abstract class TransitBase {
    private String DEVICE_ID = "deviceID";
    private String TRANSACTION_KEY = "transactionKey";

    private String deviceId;
    private String transactionKey;

    protected JSONObject transitSerialized = new JSONObject();

    public TransitBase(JSONObject jsonObject) throws JSONException {
        setDeviceId(JsonUtil.getString(jsonObject, DEVICE_ID));
        setTransactionKey(JsonUtil.getString(jsonObject, TRANSACTION_KEY));
    }

    public TransitBase(String deviceId, String transactionKey) {
        this.deviceId = deviceId;
        this.transactionKey = transactionKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
    }

    public JSONObject serialize() throws JSONException {
        put(DEVICE_ID, getDeviceId());
        put(TRANSACTION_KEY, getTransactionKey());
        return transitSerialized;
    }


    protected void put(@NonNull String name, Object value) {
        if (transitSerialized != null && value != null)
            try {
                transitSerialized.put(name, value);
            } catch (JSONException e) {
                LogUtil.e("JSON put", e.getMessage(), e);
            }
    }

}
