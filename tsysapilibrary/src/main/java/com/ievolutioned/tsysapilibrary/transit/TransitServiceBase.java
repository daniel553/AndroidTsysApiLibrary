package com.ievolutioned.tsysapilibrary.transit;

import android.os.AsyncTask;

import com.ievolutioned.tsysapilibrary.util.AppUtil;

/**
 * {@link TransitServiceBase} abstract class. Allows to the services call, cancel and handle the
 * response for TransIT services.
 * <p>
 * Created by Daniel on 08/12/2016.
 * </p>
 */

public abstract class TransitServiceBase {

    /**
     * Url base for TransIT services.
     */
    protected final String BASE_URL = AppUtil.isDebug ?
            AppUtil.DEV_URL :  //Dev
            AppUtil.PROD_URL;  //PROD
    /**
     *  Array of Transit arguments for evaluation
     */
    protected String fields[];

    /**
     * {@link AsyncTask} main task for a single call for a service.
     */
    protected AsyncTask<Void, Void, BaseResponse> task;

    /**
     * Service caller.
     *
     * @param response - {@link TransitBase} response object.
     * @return a {@link BaseResponse} response.
     */
    protected abstract BaseResponse callService(TransitBase response);

    /**
     * Executes the main task
     */
    public void execute() {
        if (task != null)
            task.execute();
    }

    /**
     * Handles the response from a service call. Success and error handler.
     *
     * @param response - {@link BaseResponse} response object.
     * @param callback - {@link TransitServiceCallback} callback.
     */
    protected void handleResponse(BaseResponse response, TransitServiceCallback callback) {
        if(response instanceof ErrorResponse){
            callback.onError(((ErrorResponse) response).getMsg(),response);
        }else if (response == null || response.getStatus() == null)
            callback.onError("Error to call the service", null);
        else if (response.getStatus().contentEquals(BaseResponse.FAIL))
            callback.onError(response.getResponseMessage(), response);
        else
            callback.onSuccess(response.getResponseMessage(), response);
    }

    /**
     * Cancels the task if it is running.
     */
    public void cancel() {
        if (task != null)
            task.cancel(true);
    }

}
