package com.ievolutioned.tsysapilibrary.transit.model;

import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Void Transaction object. Contains the main definitions for a void.
 * <p>
 * Example JSON:
 * {"TipAdjustment": {
 * "deviceID": "000000000000",
 * "transactionKey": "xxxxxxxxxxxxxxxxxxxxxxxxxx",
 * "tip": "0.10",
 * "transactionID": "111111111111111111"
 * }}
 * </p>
 * </p>
 * Created by Daniel on 08/12/2016.
 */

public class TipAdjustment extends TransitBase {

    public final String TIP = "tip";
    public final String TRANSACTION_ID = "transactionID";

    private String tip;
    private String transactionID;

    public TipAdjustment(String deviceId, String transactionKey, String tip, String transactionID) {
        super(deviceId, transactionKey);
        this.tip = tip;
        this.transactionID = transactionID;
    }

    public TipAdjustment(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        setTip(JsonUtil.getString(jsonObject, TIP));
        setTransactionID(JsonUtil.getString(jsonObject, TRANSACTION_ID));
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public JSONObject serialize() throws JSONException {
        super.serialize();
        put(TIP, getTip());
        put(TRANSACTION_ID, getTransactionID());
        return transitSerialized;
    }
}
