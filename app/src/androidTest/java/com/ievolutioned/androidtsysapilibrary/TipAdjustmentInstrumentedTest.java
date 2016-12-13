package com.ievolutioned.androidtsysapilibrary;

import android.support.test.runner.AndroidJUnit4;

import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.cardservices.SaleService;
import com.ievolutioned.tsysapilibrary.transit.cardservices.TipAdjustmentService;
import com.ievolutioned.tsysapilibrary.transit.model.Sale;
import com.ievolutioned.tsysapilibrary.transit.model.TipAdjustment;

import org.junit.Before;
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
public class TipAdjustmentInstrumentedTest {


    private SaleService.SaleResponse saleResponse = null;
    String deviceId = "88300000228401";
    String transactionKey = "1SN6NMT7MI3XQ8SSJSL592DAHNVGCQC0";

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

    private BaseResponse tipAdjustmentResponse;

    @Test
    public void tipAdjustmentServiceCallPassTest() throws Exception {
        if (saleResponse == null || saleResponse.getTransactionID() == null
                || saleResponse.getTransactionID().isEmpty())
            throw new IllegalArgumentException("Sale response must not be null");

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String tipamount = "0.10";
        TipAdjustment tip = new TipAdjustment(deviceId, transactionKey, tipamount,
                saleResponse.getTransactionID());
        new TipAdjustmentService(tip, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                tipAdjustmentResponse = response;
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
        assertTrue(tipAdjustmentResponse.getStatus().contentEquals(SaleService.SaleResponse.PASS));
    }

}
