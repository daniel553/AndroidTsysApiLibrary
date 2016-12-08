package com.ievolutioned.tsysapilibrary.transit.cardservices;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.ievolutioned.tsysapilibrary.net.NetUtil;
import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.model.Sale;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Void;

/**
 * Created by Daniel on 07/12/2016.
 */

public class SaleService extends TransitServiceBase {
    private String TAG = SaleService.class.getName();

    private String URL = BASE_URL + "Sale";

    public SaleService(final Sale sale, final TransitServiceCallback callback) {
        task = new AsyncTask<Void, Void, BaseResponse>() {
            @Override
            protected SaleResponse doInBackground(Void... voids) {
                if (!isCancelled())
                    return callService(sale);
                return null;
            }

            @Override
            protected void onPostExecute(BaseResponse baseResponse) {
                super.onPostExecute(baseResponse);
                if (!isCancelled())
                    handleResponse(baseResponse, callback);
                else
                    callback.onCancel();
            }
        };
    }

    @Nullable
    private SaleResponse callService(Sale sale) {
        if (sale == null || sale.getDeviceId() == null || sale.getDeviceId().isEmpty())
            return null;
        try {
            String response = NetUtil.post(URL, sale.serialize().toString(), NetUtil.CONTENT_TYPE_JSON);
            JSONObject json = new JSONObject(response);
            JSONObject saleResponse = json.getJSONObject("SaleResponse");
            return new SaleResponse(saleResponse);
        } catch (JSONException je) {
            LogUtil.e(TAG, je.getMessage(), je);
            return null;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }


    public class SaleResponse extends BaseResponse {

        private String AUTH_CODE = "authCode";
        private String HOST_REFERENCE_NUMBER = "hostReferenceNumber";
        private String TASK_ID = "taskID";
        private String TRANSACTION_ID = "transactionID";
        private String TRANSACTION_TIMESTAMP = "transactionTimestamp";
        private String TRANSACTION_AMOUNT = "transactionAmount";
        private String PROCESSED_AMOUNT = "processedAmount";
        private String TOTAL_AMOUNT = "totalAmount";
        private String ADDRESS_VERIFICATION_CODE = "addressVerificationCode";
        private String CARD_TYPE = "cardType";
        private String MASKED_CARD_NUMBER = "maskedCardNumber";
        private String COMMERCIAL_CARD = "commercialCard";
        private String CUSTOMER_RECEIPT = "customerReceipt";
        private String MERCHANT_RECEIPT = "merchantReceipt";

        private String authCode;
        private String hostReferenceNumber;
        private String taskID;
        private String transactionID;
        private String transactionTimestamp;
        private String transactionAmount;
        private String processedAmount;
        private String totalAmount;
        private String addressVerificationCode;
        private String cardType;
        private String maskedCardNumber;
        private String commercialCard;
        private String customerReceipt;
        private String merchantReceipt;

        private SaleResponse(JSONObject jsonObject) throws JSONException {
            super(jsonObject);
            setAuthCode(jsonObject.getString(AUTH_CODE));
            setHostReferenceNumber(jsonObject.getString(HOST_REFERENCE_NUMBER));
            setTaskID(jsonObject.getString(TASK_ID));
            setTransactionID(jsonObject.getString(TRANSACTION_ID));
            setTransactionTimestamp(jsonObject.getString(TRANSACTION_TIMESTAMP));
            setTransactionAmount(jsonObject.getString(TRANSACTION_AMOUNT));
            setProcessedAmount(jsonObject.getString(PROCESSED_AMOUNT));
            setTotalAmount(jsonObject.getString(TOTAL_AMOUNT));
            setAddressVerificationCode(jsonObject.getString(ADDRESS_VERIFICATION_CODE));
            setCardType(jsonObject.getString(CARD_TYPE));
            setMaskedCardNumber(jsonObject.getString(MASKED_CARD_NUMBER));
            setCommercialCard(jsonObject.getString(COMMERCIAL_CARD));
            setCustomerReceipt(jsonObject.getString(CUSTOMER_RECEIPT));
            setMerchantReceipt(jsonObject.getString(MERCHANT_RECEIPT));
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

        public String getTransactionAmount() {
            return transactionAmount;
        }

        public void setTransactionAmount(String transactionAmount) {
            this.transactionAmount = transactionAmount;
        }

        public String getProcessedAmount() {
            return processedAmount;
        }

        public void setProcessedAmount(String processedAmount) {
            this.processedAmount = processedAmount;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getAddressVerificationCode() {
            return addressVerificationCode;
        }

        public void setAddressVerificationCode(String addressVerificationCode) {
            this.addressVerificationCode = addressVerificationCode;
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

        public String getCommercialCard() {
            return commercialCard;
        }

        public void setCommercialCard(String commercialCard) {
            this.commercialCard = commercialCard;
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

}
