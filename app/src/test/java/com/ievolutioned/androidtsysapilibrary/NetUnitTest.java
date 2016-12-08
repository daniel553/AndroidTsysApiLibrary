package com.ievolutioned.androidtsysapilibrary;

import android.util.Log;

import com.ievolutioned.tsysapilibrary.net.NetUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel on 08/12/2016.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class NetUnitTest {

    @Before
    public void setUp() throws Exception{
        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void networkPost_isCalled() throws Exception {
        String json = "{ \"method\" : \"guru.test\", \"params\" : [ \"Guru\" ], \"id\" : 123 }";
        String result = NetUtil.post("https://gurujsonrpc.appspot.com/guru", json, NetUtil.CONTENT_TYPE_JSON);
        assertTrue(!result.isEmpty());
    }
}
