package com.ievolutioned.androidtsysapilibrary;

import android.support.test.runner.AndroidJUnit4;

import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.cardservices.AuthService;
import com.ievolutioned.tsysapilibrary.transit.cardservices.SaleService;
import com.ievolutioned.tsysapilibrary.transit.model.Auth;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AuthInstrumentedTest {

    private AuthService.AuthResponse authResponse = null;

    private String deviceId = "88300000228401";
    private String transactionKey = "1SN6NMT7MI3XQ8SSJSL592DAHNVGCQC0";

    @Test
    public void authServiceCallPassTest() throws Exception {
        authResponse = null;
        String cardDataSource = Auth.CardDataSources.MANUAL;
        String transactionAmount = "0.10";
        String cardNumber = "5415920054179210";
        String expirationDate = "0819";

        Auth auth = new Auth(deviceId, transactionKey, cardDataSource, transactionAmount, cardNumber, expirationDate);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new AuthService(auth, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                countDownLatch.countDown();
                authResponse = (AuthService.AuthResponse) response;
            }

            @Override
            public void onError(String msg, BaseResponse response) {
                countDownLatch.countDown();
            }

            @Override
            public void onCancel() {
                countDownLatch.countDown();
            }
        }).execute();

        while (countDownLatch.getCount() > 0) {
            countDownLatch.await(1, TimeUnit.SECONDS);
        }
        assertTrue(authResponse.getStatus().contentEquals(SaleService.SaleResponse.PASS));
    }


}