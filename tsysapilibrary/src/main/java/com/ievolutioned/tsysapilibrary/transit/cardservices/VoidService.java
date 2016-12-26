package com.ievolutioned.tsysapilibrary.transit.cardservices;

import android.os.AsyncTask;

import com.ievolutioned.tsysapilibrary.net.NetUtil;
import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.ErrorResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceBase;
import com.ievolutioned.tsysapilibrary.transit.model.Void;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link VoidService} service class. Allows to call the service of void
 * and gets a response
 * <p>
 * Created by Daniel on 07/12/2016.
 * </p>
 */
public class VoidService extends TransitServiceBase {
    private static final String TAG = VoidService.class.getName();
    public String URL = BASE_URL + "Void";
    private Void voidObject = null;

    /**
     * {@link VoidService} instance
     *
     * @return an instance
     */
    public static VoidService getInstance() {
        synchronized (VoidService.class) {
            if (instance == null || !(instance instanceof VoidService))
                instance = new VoidService();
            return (VoidService) instance;
        }
    }

    private VoidService() {
    }

    @Override
    protected void buildTask() {
        task = new AsyncTask<java.lang.Void, java.lang.Void, BaseResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (callback == null)
                    throw new NullPointerException("callback must not be null");
                if (voidObject == null)
                    throw new NullPointerException("void object must not be null");
            }

            @Override
            protected BaseResponse doInBackground(java.lang.Void... voids) {
                if (!isCancelled())
                    return callService(voidObject);
                else return null;
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
        Void voidObject = (Void) baseResponse;
        try {
            JSONObject j = voidObject.serialize();
            fields = new String[]{Void.TRANSACTION_KEY, Void.DEVICE_ID,
                    Void.TRANSACTION_ID};
            voidObject.validateEmptyNullFields(fields);
            String response = NetUtil.post(URL, voidObject.serialize().toString(),
                    NetUtil.CONTENT_TYPE_JSON);
            JSONObject json = new JSONObject(response);
            JSONObject voidResponse = json.getJSONObject("VoidResponse");
            return new VoidResponse(voidResponse);
        } catch (JSONException je) {
            LogUtil.e(TAG, je.getMessage(), je);
            return null;
        } catch (IllegalArgumentException ia) {
            try {
                return new ErrorResponse(ia.getMessage(), ia);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void setVoidObject(Void voidObject) {
        this.voidObject = voidObject;
    }

    /**
     * {@link VoidResponse} response class. Contains the specific attributes for void response from service
     */
    public class VoidResponse extends BaseResponse {

        private final String ORDER_NUMBER = "orderNumber";
        private final String EXTERNAL_REFERENCE_ID = "externalReferenceID";
        private final String VOIDED_AMOUNT = "voidedAmount";

        private String orderNumber;
        private String externalReferenceID;
        private String voidedAmount;

        public VoidResponse(JSONObject jsonObject) throws JSONException {
            super(jsonObject);
            setOrderNumber(JsonUtil.getString(jsonObject, ORDER_NUMBER));
            setExternalReferenceID(JsonUtil.getString(jsonObject, EXTERNAL_REFERENCE_ID));
            setVoidedAmount(JsonUtil.getString(jsonObject, VOIDED_AMOUNT));
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getExternalReferenceID() {
            return externalReferenceID;
        }

        public void setExternalReferenceID(String externalReferenceID) {
            this.externalReferenceID = externalReferenceID;
        }

        public String getVoidedAmount() {
            return voidedAmount;
        }

        public void setVoidedAmount(String voidedAmount) {
            this.voidedAmount = voidedAmount;
        }
    }
}
