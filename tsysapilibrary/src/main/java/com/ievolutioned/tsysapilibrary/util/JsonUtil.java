package com.ievolutioned.tsysapilibrary.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 08/12/2016.
 */

public class JsonUtil {

    public static String getString(JSONObject jsonObject, String name) {
        if (jsonObject != null && jsonObject.has(name))
            try {
                return jsonObject.getString(name);
            } catch (JSONException e) {
                return null;
            }
        return null;
    }
}
