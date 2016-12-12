package com.ievolutioned.tsysapilibrary.transit.cardservices;

import android.os.AsyncTask;

import com.ievolutioned.tsysapilibrary.net.NetUtil;
import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.model.Void;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 07/12/2016.
 */

public class VoidService extends TransitServiceBase {
    private static final String TAG = VoidService.class.getName();
    public String URL = BASE_URL + "Void";

    public VoidService(final Void voidObject, final TransitServiceCallback callback) {
        task = new AsyncTask<java.lang.Void, java.lang.Void, BaseResponse>() {
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
        if (voidObject == null || voidObject.getDeviceId() == null || voidObject.getDeviceId().isEmpty())
            return null;
        try {
            String response = NetUtil.post(URL, voidObject.serialize().toString(), NetUtil.CONTENT_TYPE_JSON);
            JSONObject json = new JSONObject(response);
            JSONObject voidResponse = json.getJSONObject("VoidResponse");
            return new VoidResponse(voidResponse);
        } catch (JSONException je) {
            LogUtil.e(TAG, je.getMessage(), je);
            return null;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }


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
