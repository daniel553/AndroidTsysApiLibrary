package com.ievolutioned.tsysapilibrary.transit.model;

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

    public final String TRANSACTION_ID = "transactionID";
    public final String EXTERNAL_REFERENCE_ID = "externalReferenceID";

    private String transactionID;
    private String externalReferenceID;

    public Void(String deviceId, String transactionKey, String transactionID, String externalReferenceID) {
        super(deviceId, transactionKey);
        this.transactionID = transactionID;
        this.externalReferenceID = externalReferenceID;
    }

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
        put(TRANSACTION_ID, getTransactionID());
        put(EXTERNAL_REFERENCE_ID, getExternalReferenceID());
        return transitSerialized;
    }
}
