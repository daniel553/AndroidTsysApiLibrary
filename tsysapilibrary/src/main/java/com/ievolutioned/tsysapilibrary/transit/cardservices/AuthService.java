package com.ievolutioned.tsysapilibrary.transit.cardservices;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.ievolutioned.tsysapilibrary.net.NetUtil;
import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.CardDataSources;
import com.ievolutioned.tsysapilibrary.transit.ErrorResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.model.Auth;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link AuthService} service class. Allows to call the service of Auth and get a response
 * <p>
 * Created by Daniel on 07/12/2016.
 * </p>
 */
public class AuthService extends TransitServiceBase {
    private String TAG = AuthService.class.getName();

    public String URL = BASE_URL + "Auth";

    /**
     * {@link AuthService} service task builder.
     * <p>
     * It doesn't execute the code, use @see TransitBase#execute()
     * </p>
     *
     * @param auth     - {@link Auth} object.
     * @param callback - {@link TransitServiceCallback} callback.
     */
    public AuthService(@NonNull final Auth auth, @NonNull final TransitServiceCallback callback) {
        task = new AsyncTask<Void, Void, BaseResponse>() {
            @Override
            protected BaseResponse doInBackground(Void... voids) {
                if (!isCancelled())
                    return callService(auth);
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
    protected BaseResponse callService(TransitBase transit) {
        Auth auth = (Auth) transit;
        try {
            JSONObject j = auth.serialize();
            validate(auth);
            String response = NetUtil.post(URL, j.toString(), NetUtil.CONTENT_TYPE_JSON);
            JSONObject json = new JSONObject(response);
            JSONObject authResponse = json.getJSONObject("AuthResponse");
            return new AuthResponse(authResponse);
        } catch (JSONException je) {
            LogUtil.e(TAG, je.getMessage(), je);
            return null;
        } catch (IllegalArgumentException ia) {
            try {
                return new ErrorResponse(ia.getMessage(), ia);
            } catch (JSONException je) {
                LogUtil.e(TAG, je.getMessage(), je);
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
     * @param auth - {@link Auth} object that will be used to call for the TransIT service.
     * @throws IllegalArgumentException
     */
    private void validate(Auth auth) throws IllegalArgumentException{
        fields= new String[] {Auth.DEVICE_ID,Auth.TRANSACTION_KEY,Auth.CARD_DATA_SOURCE};
        auth.validateEmptyNullFields(fields);
        if(auth.getCardDataSource()== CardDataSources.MANUAL) {
            fields = new String[]{Auth.DEVICE_ID, Auth.TRANSACTION_KEY, Auth.CARD_NUMBER,
                    Auth.CARD_DATA_SOURCE, Auth.EXPIRATION_DATE, Auth.TRANSACTION_AMOUNT};
            auth.validateEmptyNullFields(fields);
        }else if(auth.getCardDataSource()==CardDataSources.SWIPE){
            fields= new String[] {/*Add required fields for SWIPE*/};
            auth.validateEmptyNullFields(fields);
        }
    }


    /**
     * {@link AuthResponse} response class. Contains the specific attributes for auth response from service
     */
    public class AuthResponse extends BaseResponse {
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

        /**
         * {@link AuthResponse} constructor.
         *
         * @param jsonObject - {@link JSONObject} to be parsed
         * @throws JSONException
         */
        private AuthResponse(JSONObject jsonObject) throws JSONException {
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
