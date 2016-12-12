package com.ievolutioned.tsysapilibrary.transit;

import android.os.AsyncTask;

import com.ievolutioned.tsysapilibrary.util.AppUtil;

/**
 * Created by Daniel on 08/12/2016.
 */

public abstract class TransitServiceBase {

    protected final String BASE_URL = AppUtil.isDebug ?
            "https://stagegw.transnox.com/servlets/TransNox_API_Server/" : //Dev
            "https://stagegw.transnox.com/servlets/TransNox_API_Server/";  //PROD

    protected AsyncTask<Void, Void, BaseResponse> task;
    protected abstract BaseResponse callService(TransitBase response);

    public void cancel() {
        if (task != null)
            task.cancel(true);
    }

    protected void handleResponse(BaseResponse response, TransitServiceCallback callback) {
        if (response == null || response.getStatus() == null)
            callback.onError("Error to call the service", null);
        else if (response.getStatus().contentEquals(BaseResponse.FAIL))
            callback.onError(response.getResponseMessage(), response);
        else
            callback.onSuccess(response.getResponseMessage(), response);
    }

    public void execute() {
        if (task != null)
            task.execute();
    }

}
