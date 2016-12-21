package com.ievolutioned.tsysapilibrary.transit;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marco on 20/12/2016.
 */

/**
 * {@link ErrorResponse} response class. Contains the main attributes and
 * methods for the TransIT services in case of an error.
 */

public class ErrorResponse extends BaseResponse {
        private String msg;
        private Throwable exception;

        /**
         * {@link BaseResponse} constructors. Builds a base object from {@link JSONObject} object.
         * <p>
         * For more information {@see https://stagetoolkit.transnox.com/site/sandbox/Sandbox}
         * </p>
         *
         * @param jsonObject - {@link JSONObject} object of response
         * @throws JSONException
         */
        public ErrorResponse(JSONObject jsonObject) throws JSONException {
            super(jsonObject);
        }

    /**
     * {@link ErrorResponse} contructor. Builds an base object from {@link IllegalArgumentException}
     * @param msg - String with the error message
     * @param exception - The exception thrown
     * @throws JSONException
     */
        public ErrorResponse(String msg, Throwable exception) throws JSONException {
            super(new JSONObject());
            this.msg=msg;
            this.exception=exception;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Throwable getException() {
            return exception;
        }

        public void setException(Throwable exception) {
            this.exception = exception;
        }

}
