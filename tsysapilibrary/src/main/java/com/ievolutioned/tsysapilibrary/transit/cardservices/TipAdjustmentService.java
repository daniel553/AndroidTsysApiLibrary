package com.ievolutioned.tsysapilibrary.transit.cardservices;

import android.os.AsyncTask;

import com.ievolutioned.tsysapilibrary.net.NetUtil;
import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceBase;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.model.TipAdjustment;
import com.ievolutioned.tsysapilibrary.util.JsonUtil;
import com.ievolutioned.tsysapilibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 07/12/2016.
 */

public class TipAdjustmentService extends TransitServiceBase {

    private String TAG = SaleService.class.getName();

    public String URL = BASE_URL + "TipAdjustment";

    public TipAdjustmentService(final TipAdjustment tipAdjustment,
                                final TransitServiceCallback callback) {
        task = new AsyncTask<Void, Void, BaseResponse>() {
            @Override
            protected BaseResponse doInBackground(Void... voids) {
                if (!isCancelled())
                    return callService(tipAdjustment);
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
        TipAdjustment tipAdjustment = (TipAdjustment) baseResponse;
        if (tipAdjustment == null || tipAdjustment.getDeviceId() == null
                || tipAdjustment.getDeviceId().isEmpty())
            return null;
        try {
            String response = NetUtil.post(URL, tipAdjustment.serialize().toString(),
                    NetUtil.CONTENT_TYPE_JSON);
            JSONObject json = new JSONObject(response);
            JSONObject tipResponse = json.getJSONObject("TipAdjustmentResponse");
            return new TipAdjustmentResponse(tipResponse);
        } catch (JSONException je) {
            LogUtil.e(TAG, je.getMessage(), je);
            return null;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }


    public class TipAdjustmentResponse extends BaseResponse {
        private String TOTAL_AMOUNT = "totalAmount";
        private String TIP = "tip";
        private String totalAmount;
        private String tip;

        private TipAdjustmentResponse(JSONObject jsonObject) throws JSONException {
            super(jsonObject);
            setTotalAmount(JsonUtil.getString(jsonObject, TOTAL_AMOUNT));
            setTip(JsonUtil.getString(jsonObject, TIP));
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }
    }

}
