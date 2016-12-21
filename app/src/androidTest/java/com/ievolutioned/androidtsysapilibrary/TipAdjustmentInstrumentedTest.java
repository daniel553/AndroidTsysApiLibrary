package com.ievolutioned.androidtsysapilibrary;

import android.support.test.runner.AndroidJUnit4;

import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.CardDataSources;
import com.ievolutioned.tsysapilibrary.transit.ErrorResponse;
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

    private CountDownLatch delay= new CountDownLatch(1);
    private SaleService.SaleResponse saleResponse = null;
    private ErrorResponse errorResponse=null;
    private String deviceId = "88300000228401";
    private String transactionKey = "1SN6NMT7MI3XQ8SSJSL592DAHNVGCQC0";
    private String message;

    @Before
    public void setUp() throws Exception {
        delay.await(6,TimeUnit.SECONDS);
        while (delay.getCount()>0){
            delay.countDown();
        }
        saleResponse = null;
        errorResponse=null;
        String cardDataSource = CardDataSources.MANUAL;
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
                if(response instanceof TipAdjustmentService.TipAdjustmentResponse)
                    tipAdjustmentResponse=response;
                else if(response instanceof ErrorResponse)
                    errorResponse=(ErrorResponse) response;
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
                if(response instanceof TipAdjustmentService.TipAdjustmentResponse)
                    tipAdjustmentResponse=response;
                else if(response instanceof ErrorResponse)
                    errorResponse=(ErrorResponse) response;
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
        assertTrue(tipAdjustmentResponse.getStatus().contentEquals(TipAdjustmentService.TipAdjustmentResponse.PASS));
    }

    @Test
    public void testThatNullTipAmountFails() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String tipamount = "";
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
                if(response instanceof TipAdjustmentService.TipAdjustmentResponse)
                    tipAdjustmentResponse=response;
                else if(response instanceof ErrorResponse)
                    errorResponse=(ErrorResponse) response;
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
        assertTrue(tipAdjustmentResponse == null);
        assertTrue(errorResponse.getMsg().contains("must not"));
    }

    @Test
    public void testThatTipAmountIsZeroSuccess() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String tipamount = "0.00";
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
                if(response instanceof TipAdjustmentService.TipAdjustmentResponse)
                    tipAdjustmentResponse=response;
                else if(response instanceof ErrorResponse)
                    errorResponse=(ErrorResponse) response;
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
        assertTrue(tipAdjustmentResponse.getStatus().contentEquals(TipAdjustmentService.TipAdjustmentResponse.PASS));
        assertTrue(tipAdjustmentResponse.getResponseCode().contentEquals("A0000"));

    }

    @Test
    public void testThatNegativeTipAmountFails() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String tipamount = "-0.10";
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
                if(response instanceof TipAdjustmentService.TipAdjustmentResponse)
                    tipAdjustmentResponse=response;
                else if(response instanceof ErrorResponse)
                    errorResponse=(ErrorResponse) response;
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
        assertTrue(tipAdjustmentResponse.getStatus().contentEquals(TipAdjustmentService.TipAdjustmentResponse.FAIL));
    }

    @Test
    public void testThatNullTransactionIdFails() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String tipamount = "0.10";
        TipAdjustment tip = new TipAdjustment(deviceId, transactionKey, tipamount,
                null);
        new TipAdjustmentService(tip, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                tipAdjustmentResponse = response;
                countDownLatch.countDown();
            }

            @Override
            public void onError(String msg, BaseResponse response) {
                if(response instanceof TipAdjustmentService.TipAdjustmentResponse)
                    tipAdjustmentResponse=response;
                else if(response instanceof ErrorResponse)
                    errorResponse=(ErrorResponse) response;
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
        assertTrue(tipAdjustmentResponse == null);
        assertTrue(errorResponse.getMsg().contains("must not"));
    }

    @Test
    public void testThatInvalidTransactionIdFails() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String tipamount = "0.10";
        String transactionID = saleResponse.getTransactionID() + "xx";
        TipAdjustment tip = new TipAdjustment(deviceId, transactionKey, tipamount,
                transactionID);
        new TipAdjustmentService(tip, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                tipAdjustmentResponse = response;
                countDownLatch.countDown();
            }

            @Override
            public void onError(String msg, BaseResponse response) {
                if(response instanceof TipAdjustmentService.TipAdjustmentResponse)
                    tipAdjustmentResponse=response;
                else if(response instanceof ErrorResponse)
                    errorResponse=(ErrorResponse) response;
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
        assertTrue(tipAdjustmentResponse.getStatus().contentEquals(TipAdjustmentService.TipAdjustmentResponse.FAIL));
    }

}
