package com.ievolutioned.tsysapilibrary.transit;

import com.ievolutioned.tsysapilibrary.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link BaseResponse} response class. Contains the main attributes and constructors for the
 * TransIT services
 * <p>
 * Created by Daniel on 08/12/2016.
 * </p>
 */
public abstract class BaseResponse {

    public static final String FAIL = "FAIL";
    public static final String PASS = "PASS";

    private String STATUS = "status";
    private String RESPONSE_CODE = "responseCode";
    private String RESPONSE_MESSAGE = "responseMessage";
    private String AUTH_CODE = "authCode";
    private String HOST_REFERENCE_NUMBER = "hostReferenceNumber";
    private String TASK_ID = "taskID";
    private String TRANSACTION_ID = "transactionID";
    private String TRANSACTION_TIMESTAMP = "transactionTimestamp";
    private String CARD_TYPE = "cardType";
    private String MASKED_CARD_NUMBER = "maskedCardNumber";
    private String CUSTOMER_RECEIPT = "customerReceipt";
    private String MERCHANT_RECEIPT = "merchantReceipt";

    private String status;
    private String responseCode;
    private String responseMessage;
    private String authCode;
    private String hostReferenceNumber;
    private String taskID;
    private String transactionID;
    private String transactionTimestamp;
    private String cardType;
    private String maskedCardNumber;
    private String customerReceipt;
    private String merchantReceipt;


    /**
     * {@link BaseResponse} constructors. Builds a base object from {@link JSONObject} object.
     * <p>
     * For more information {@see https://stagetoolkit.transnox.com/site/sandbox/Sandbox}
     * </p>
     *
     * @param jsonObject - {@link JSONObject} object of response
     * @throws JSONException
     */
    public BaseResponse(JSONObject jsonObject) throws JSONException {
        setStatus(JsonUtil.getString(jsonObject, STATUS));
        setResponseCode(JsonUtil.getString(jsonObject, RESPONSE_CODE));
        setResponseMessage(JsonUtil.getString(jsonObject, RESPONSE_MESSAGE));

        //Sale, void
        setAuthCode(JsonUtil.getString(jsonObject, AUTH_CODE));
        setHostReferenceNumber(JsonUtil.getString(jsonObject, HOST_REFERENCE_NUMBER));
        setTaskID(JsonUtil.getString(jsonObject, TASK_ID));
        setTransactionID(JsonUtil.getString(jsonObject, TRANSACTION_ID));
        setTransactionTimestamp(JsonUtil.getString(jsonObject, TRANSACTION_TIMESTAMP));
        setCardType(JsonUtil.getString(jsonObject, CARD_TYPE));
        setMaskedCardNumber(JsonUtil.getString(jsonObject, MASKED_CARD_NUMBER));
        setCustomerReceipt(JsonUtil.getString(jsonObject, CUSTOMER_RECEIPT));
        setMerchantReceipt(JsonUtil.getString(jsonObject, MERCHANT_RECEIPT));
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getHostReferenceNumber() {
        return hostReferenceNumber;
    }

    public void setHostReferenceNumber(String hostReferenceNumber) {
        this.hostReferenceNumber = hostReferenceNumber;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(String transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public String getCustomerReceipt() {
        return customerReceipt;
    }

    public void setCustomerReceipt(String customerReceipt) {
        this.customerReceipt = customerReceipt;
    }

    public String getMerchantReceipt() {
        return merchantReceipt;
    }

    public void setMerchantReceipt(String merchantReceipt) {
        this.merchantReceipt = merchantReceipt;
    }


}
