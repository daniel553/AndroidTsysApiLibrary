package com.ievolutioned.tsysapilibrary.transit;

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
        setDeviceId(jsonObject.getString(DEVICE_ID));
        setTransactionKey(jsonObject.getString(TRANSACTION_KEY));
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
        transitSerialized.put(DEVICE_ID, getDeviceId());
        transitSerialized.put(TRANSACTION_KEY, getTransactionKey());
        return transitSerialized;
    }

}
