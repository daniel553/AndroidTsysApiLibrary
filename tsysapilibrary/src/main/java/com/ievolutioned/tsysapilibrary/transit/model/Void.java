package com.ievolutioned.tsysapilibrary.transit.model;

import android.support.annotation.NonNull;

import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Void Transaction object. Contains the main definitions for a void.
 * <p>
 * Example JSON:
 * {"Void": {
 * "deviceID": "88300000228401",
 * "transactionKey": "1SN6NMT7MI3XQ8SSJSL592DAHNVGCQC0",
 * "transactionID": "123213123123"
 * }}
 * </p>
 * </p>
 * Created by Daniel on 08/12/2016.
 */

public class Void extends TransitBase {

    public static final String TRANSACTION_ID = "transactionID";
    public static final String EXTERNAL_REFERENCE_ID = "externalReferenceID";

    private String transactionID;
    private String externalReferenceID;

    /**
     * {@link Void} constructor.
     *
     * @param deviceId            - unique identifier of device
     * @param transactionKey      - unique transaction key
     * @param transactionID       - transaction identifier
     * @param externalReferenceID - external reference identifier
     */
    public Void(@NonNull String deviceId, @NonNull String transactionKey,
                @NonNull String transactionID, @NonNull String externalReferenceID) {
        super(deviceId, transactionKey);
        this.transactionID = transactionID;
        this.externalReferenceID = externalReferenceID;
    }

    /**
     * {@link Void} constructor.
     *
     * @param jsonObject - {@link JSONObject} object, see the example above.
     * @throws JSONException
     */
    public Void(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        setTransactionID(JsonUtil.getString(jsonObject, TRANSACTION_ID));
        setExternalReferenceID(JsonUtil.getString(jsonObject, EXTERNAL_REFERENCE_ID));
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getExternalReferenceID() {
        return externalReferenceID;
    }

    public void setExternalReferenceID(String externalReferenceID) {
        this.externalReferenceID = externalReferenceID;
    }

    @Override
    public JSONObject serialize() throws JSONException {
        super.serialize();
        put(TRANSACTION_ID, getTransactionID());
        put(EXTERNAL_REFERENCE_ID, getExternalReferenceID());
        return transitSerialized;
    }
}
