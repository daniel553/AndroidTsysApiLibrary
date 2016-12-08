package com.ievolutioned.tsysapilibrary.net;

import android.support.annotation.NonNull;

import com.ievolutioned.tsysapilibrary.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Network connection manager class. Performs a set of operation about
 * networking
 * <p>
 * Created by Daniel on 08/12/2016.
 */

public class NetUtil {


    public static final String CONTENT_TYPE_JSON = "application/json;";
    private static final String METHOD_POST = "POST";
    private static final String UTF8 = "UTF-8";

    /**
     * POST method, gets a post request
     *
     * @param url  the URL
     * @param json a JSON string
     * @return the response
     * @throws Exception
     */
    public static String post(String url, String json, String contentType) throws Exception {
        if (url == null || url.isEmpty() || json == null || json.isEmpty() || contentType == null
                || contentType.isEmpty())
            throw new IllegalArgumentException("Parameters must not be null or empty");

        HttpURLConnection connection = null;
        try {
            URL contentUrl = new URL(url);
            connection = (HttpURLConnection) contentUrl.openConnection();
            connection.setRequestMethod(METHOD_POST);


            String postLenght =  String.valueOf(json.getBytes(UTF8).length);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Length", postLenght);
            connection.setRequestProperty("Content-Type", contentType + " charset=UTF-8");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.connect();

            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes(UTF8));
            os.flush();
            os.close();

            if (connection.getResponseCode() >= 400) {
                throw new IOException(connection.getResponseCode() + ":"
                        + readStream(connection.getErrorStream()));
            }
            return readStream(connection.getInputStream());
        } catch (Exception e) {
            throw e;
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    /**
     * Reads the input stream.
     *
     * @param inputStream a InputStream
     * @return a String that contains the <b>inputStream</b>
     * @throws IOException
     */
    @NonNull
    private static String readStream(InputStream inputStream)
            throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        LogUtil.d(NetUtil.class.getName(), sb.toString());
        return sb.toString();
    }

}
