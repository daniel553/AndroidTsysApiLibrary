package com.ievolutioned.tsysapilibrary.transit.cardservices;

import android.os.AsyncTask;

import com.ievolutioned.tsysapilibrary.net.NetUtil;
import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.model.Sale;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 07/12/2016.
 */

public class SaleService extends TransitServiceBase {
    private String TAG = SaleService.class.getName();

    public String URL = BASE_URL + "Sale";

    public SaleService(final Sale sale, final TransitServiceCallback callback) {
        task = new AsyncTask<Void, Void, BaseResponse>() {
            @Override
            protected BaseResponse doInBackground(Void... voids) {
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

    @Override
    protected BaseResponse callService(TransitBase baseResponse) {
        Sale sale = (Sale) baseResponse;
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
        private String TRANSACTION_AMOUNT = "transactionAmount";
        private String PROCESSED_AMOUNT = "processedAmount";
        private String TOTAL_AMOUNT = "totalAmount";
        private String ADDRESS_VERIFICATION_CODE = "addressVerificationCode";
        private String COMMERCIAL_CARD = "commercialCard";
        private String transactionAmount;
        private String processedAmount;
        private String totalAmount;
        private String addressVerificationCode;
        private String commercialCard;

        private SaleResponse(JSONObject jsonObject) throws JSONException {
            super(jsonObject);
            setTransactionAmount(JsonUtil.getString(jsonObject, TRANSACTION_AMOUNT));
            setProcessedAmount(JsonUtil.getString(jsonObject, PROCESSED_AMOUNT));
            setTotalAmount(JsonUtil.getString(jsonObject, TOTAL_AMOUNT));
            setAddressVerificationCode(JsonUtil.getString(jsonObject, ADDRESS_VERIFICATION_CODE));
            setCommercialCard(JsonUtil.getString(jsonObject, COMMERCIAL_CARD));
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

        public String getCommercialCard() {
            return commercialCard;
        }

        public void setCommercialCard(String commercialCard) {
            this.commercialCard = commercialCard;
        }
    }

}
