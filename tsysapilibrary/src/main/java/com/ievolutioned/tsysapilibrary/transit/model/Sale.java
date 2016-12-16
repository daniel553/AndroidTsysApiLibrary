package com.ievolutioned.tsysapilibrary.transit.model;

import android.support.annotation.NonNull;

import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Transaction Sale object. Contains the main definitions for a sale.
 * <p>
 * Example JSON:
 * <p>
 * {
 * "Sale": {
 * "deviceID": "000000000",
 * "transactionKey": "xxxxxxxxxxxxxxxxxxxxxxxx",
 * "cardDataSource": "MANUAL",
 * "transactionAmount": "1000",
 * "cardNumber": "54545454545454",
 * "expirationDate": "0919"
 * }
 * }
 * <p>
 * Created by Daniel on 08/12/2016.
 */

public class Sale extends TransitBase {

    private String CARD_DATA_SOURCE = "cardDataSource";
    private String TRANSACTION_AMOUNT = "transactionAmount";
    private String CARD_NUMBER = "cardNumber";
    private String EXPIRATION_DATE = "expirationDate";

    private String cardDataSource;
    private String transactionAmount;
    private String cardNumber;
    private String expirationDate;


    /**
     * Sale constructor.
     *
     * @param deviceId          - unique identifier of device
     * @param transactionKey    - unique transaction key
     * @param cardDataSource    - {@link com.ievolutioned.tsysapilibrary.transit.CardDataSources} source
     * @param transactionAmount - final transaction amount
     * @param cardNumber        - card number
     * @param expirationDate    - expiration date of card
     */
    public Sale(@NonNull String deviceId, @NonNull String transactionKey,
                @NonNull String cardDataSource, @NonNull String transactionAmount,
                @NonNull String cardNumber, @NonNull String expirationDate) {
        super(deviceId, transactionKey);
        this.cardDataSource = cardDataSource;
        this.transactionAmount = transactionAmount;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    /**
     * Sale constructor
     *
     * @param jsonObject - {@link JSONObject} object, see example above
     * @throws Exception
     */
    public Sale(JSONObject jsonObject) throws Exception {
        super(jsonObject);
        setCardDataSource(JsonUtil.getString(jsonObject, CARD_DATA_SOURCE));
        setTransactionAmount(JsonUtil.getString(jsonObject, TRANSACTION_AMOUNT));
        setCardNumber(JsonUtil.getString(jsonObject, CARD_NUMBER));
        setExpirationDate(JsonUtil.getString(jsonObject, EXPIRATION_DATE));
    }

    public String getCardDataSource() {
        return cardDataSource;
    }

    public void setCardDataSource(String cardDataSource) {
        this.cardDataSource = cardDataSource;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public JSONObject serialize() throws JSONException {
        super.serialize();
        put(CARD_DATA_SOURCE, getCardDataSource());
        put(TRANSACTION_AMOUNT, getTransactionAmount());
        put(CARD_NUMBER, getCardNumber());
        put(EXPIRATION_DATE, getExpirationDate());
        return transitSerialized;
    }
}
