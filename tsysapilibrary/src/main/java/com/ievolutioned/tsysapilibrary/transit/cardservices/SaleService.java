package com.ievolutioned.tsysapilibrary.transit.cardservices;

import android.os.AsyncTask;

import com.ievolutioned.tsysapilibrary.net.NetUtil;
import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.CardDataSources;
import com.ievolutioned.tsysapilibrary.transit.ErrorResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceBase;
import com.ievolutioned.tsysapilibrary.transit.model.Sale;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link SaleService} service class. Allows to call the service of Sale and get a response
 * <p>
 * Created by Daniel on 07/12/2016.
 * </p>
 */
public class SaleService extends TransitServiceBase {
    private String TAG = SaleService.class.getName();
    private Sale sale = null;
    public String URL = BASE_URL + "Sale";

    /**
     * Sale service instance
     *
     * @return an instance
     */
    public static SaleService getInstance() {
        synchronized (SaleService.class) {
            if (instance == null || !(instance instanceof SaleService))
                instance = new SaleService();
            return (SaleService) instance;
        }
    }

    private SaleService() {
    }

    @Override
    protected void buildTask() {
        task = new AsyncTask<Void, Void, BaseResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (callback == null)
                    throw new NullPointerException("callback must not be null");
                if (sale == null)
                    throw new NullPointerException("sale must not be null");
            }

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
        try {
            JSONObject j = sale.serialize();
            validate(sale);
            String response = NetUtil.post(URL, j.toString(), NetUtil.CONTENT_TYPE_JSON);
            JSONObject json = new JSONObject(response);
            JSONObject saleResponse = json.getJSONObject("SaleResponse");
            return new SaleResponse(saleResponse);
        } catch (JSONException je) {
            LogUtil.e(TAG, je.getMessage(), je);
            return null;
        } catch (IllegalArgumentException ia) {
            try {
                return new ErrorResponse(ia.getMessage(), ia);
            } catch (JSONException e) {
                LogUtil.e(TAG, e.getMessage());
                return null;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Validates that the required attributes for a service are not null, empty of blanck
     * spcaced, if one of the above throws an IllegalArgumentException
     *
     * @param sale - {@link Sale} object that will be used for calling the TransIT service.
     * @throws IllegalArgumentException
     */
    protected void validate(Sale sale) throws IllegalArgumentException {
        fields = new String[]{Sale.TRANSACTION_KEY, Sale.DEVICE_ID, Sale.CARD_DATA_SOURCE};
        sale.validateEmptyNullFields(fields);
        if (sale.getCardDataSource() == CardDataSources.MANUAL) {
            fields = new String[]{Sale.CARD_NUMBER, Sale.EXPIRATION_DATE, Sale.TRANSACTION_AMOUNT};
            sale.validateEmptyNullFields(fields);
        } else if (sale.getCardDataSource() == CardDataSources.SWIPE) {
            fields = new String[]{/* add SWIPE requiered fields to validate */};
            sale.validateEmptyNullFields(fields);
        }
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }


    /**
     * {@link SaleResponse} response class. Contains the specific attributes for sale response from service
     */
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
