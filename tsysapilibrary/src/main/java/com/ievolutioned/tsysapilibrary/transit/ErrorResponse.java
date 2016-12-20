package com.ievolutioned.tsysapilibrary.transit;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marco on 20/12/2016.
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
