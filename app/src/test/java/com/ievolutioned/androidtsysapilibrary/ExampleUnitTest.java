package com.ievolutioned.androidtsysapilibrary;

import com.ievolutioned.tsysapilibrary.Example;
import com.ievolutioned.tsysapilibrary.net.NetUtil;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String json = "{ \"method\" : \"guru.test\", \"params\" : [ \"Guru\" ], \"id\" : 123 }";
        NetUtil.post("https://gurujsonrpc.appspot.com/guru",json, NetUtil.CONTENT_TYPE_JSON);
        assertTrue(true);
    }
}