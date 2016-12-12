package com.ievolutioned.androidtsysapilibrary;

import android.support.test.runner.AndroidJUnit4;

import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.cardservices.SaleService;
import com.ievolutioned.tsysapilibrary.transit.cardservices.VoidService;
import com.ievolutioned.tsysapilibrary.transit.model.Sale;
import com.ievolutioned.tsysapilibrary.transit.model.Void;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel on 11/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public class VoidInstrumentedTest {

    private final String deviceId = "88300000228401";
    private final String transactionKey = "1SN6NMT7MI3XQ8SSJSL592DAHNVGCQC0";

    private SaleService.SaleResponse saleResponse = null;
    private VoidService.VoidResponse voidResponse = null;

    @Before
    public void setUp() throws Exception {

        saleResponse = null;
        String cardDataSource = Sale.CardDataSources.MANUAL;
        String transactionAmount = "0.10";
        String cardNumber = "5415920054179210";
        String expirationDate = "0819";

        Sale sale = new Sale(deviceId, transactionKey, cardDataSource, transactionAmount, cardNumber, expirationDate);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new SaleService(sale, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                saleResponse = (SaleService.SaleResponse) response;
                countDownLatch.countDown();
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
    }

    @Test
    public void voidServiceCallPassTest() throws Exception {
        if (saleResponse == null)
            throw new RuntimeException("No Sale Response!!!");

        voidResponse = null;
        final String transactionID = saleResponse.getTransactionID();

        Void voidObject = new Void(deviceId, transactionKey, transactionID, null);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new VoidService(voidObject, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                voidResponse = (VoidService.VoidResponse) response;
                countDownLatch.countDown();
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
        assertTrue(voidResponse.getStatus().contentEquals(BaseResponse.PASS));
    }

}
