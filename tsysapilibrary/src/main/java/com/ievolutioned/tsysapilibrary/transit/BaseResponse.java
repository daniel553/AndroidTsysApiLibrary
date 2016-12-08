package com.ievolutioned.tsysapilibrary.transit;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 08/12/2016.
 */

public abstract class BaseResponse {

    public static final String FAIL = "FAIL";
    public static final String PASS = "PASS";

    private String STATUS = "status";
    private String RESPONSE_CODE = "responseCode";
    private String RESPONSE_MESSAGE = "responseMessage";

    private String status;
    private String responseCode;
    private String responseMessage;

    public BaseResponse(JSONObject jsonObject) throws JSONException {
        setStatus(jsonObject.getString(STATUS));
        setResponseCode(jsonObject.getString(RESPONSE_CODE));
        setResponseMessage(jsonObject.getString(RESPONSE_MESSAGE));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
