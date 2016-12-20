package com.ievolutioned.tsysapilibrary.transit;

import android.support.annotation.NonNull;

import com.ievolutioned.tsysapilibrary.util.JsonUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

/**
 * {@link TransitBase} base class. Defines the device id and transaction key attributes
 * Serializes the main attributes of the object.
 * For the main structure {@see https://stagetoolkit.transnox.com/dynaform}
 * <p>
 * Created by Daniel on 08/12/2016.
 * </p>
 */
public abstract class TransitBase {
    public static String DEVICE_ID = "deviceID";
    public static String TRANSACTION_KEY = "transactionKey";

    private String deviceId;
    private String transactionKey;

    protected JSONObject transitSerialized = new JSONObject();

    /**
     * {@link TransitBase} constructor.
     *
     * @param jsonObject - {@link JSONObject} object.
     * @throws JSONException
     */
    public TransitBase(JSONObject jsonObject) throws JSONException {
        setDeviceId(JsonUtil.getString(jsonObject, DEVICE_ID));
        setTransactionKey(JsonUtil.getString(jsonObject, TRANSACTION_KEY));
    }

    /**
     * {@link TransitBase} constructor.
     *
     * @param deviceId       - unique identifier of device
     * @param transactionKey - unique transaction key
     */
    public TransitBase(String deviceId, String transactionKey) {
        this.deviceId = deviceId;
        this.transactionKey = transactionKey;
    }


    /**
     * Serializes the object
     *
     * @return a {@link JSONObject} serialized object
     * @throws JSONException
     */
    public JSONObject serialize() throws JSONException {
        put(DEVICE_ID, getDeviceId());
        put(TRANSACTION_KEY, getTransactionKey());
        return transitSerialized;
    }


    /**
     * Adds a new attribute to the {@link JSONObject}
     *
     * @param name  - name from json
     * @param value - value from json
     */
    protected void put(@NonNull String name, Object value) {
        if (transitSerialized != null && value != null)
            try {
                transitSerialized.put(name, value);
            } catch (JSONException e) {
                LogUtil.e("JSON put", e.getMessage(), e);
            }
    }

    public void validateEmptyNullFields(String[] fields) throws IllegalArgumentException {
        for (String v: fields){
            String data=JsonUtil.getString(this.transitSerialized,v);
            if(data==null || data.isEmpty() || data.trim().isEmpty()) {
                throw new IllegalArgumentException(v + " must not be empty or null");
            }
        }

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

}
