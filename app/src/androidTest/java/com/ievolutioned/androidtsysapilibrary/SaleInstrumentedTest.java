package com.ievolutioned.androidtsysapilibrary;

import android.support.test.runner.AndroidJUnit4;

import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.CardDataSources;
import com.ievolutioned.tsysapilibrary.transit.ErrorResponse;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.cardservices.SaleService;
import com.ievolutioned.tsysapilibrary.transit.model.Sale;

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
public class SaleInstrumentedTest {
    private CountDownLatch delay= new CountDownLatch(1);
    private SaleService.SaleResponse saleResponse = null;
    private ErrorResponse errorResponse=null;
    private String deviceId = "88300000228401";
    private String transactionKey = "1SN6NMT7MI3XQ8SSJSL592DAHNVGCQC0";
    private String cardDataSource = CardDataSources.MANUAL;

    @Before
    public void setUp() throws Exception{
        delay.await(6,TimeUnit.SECONDS);
        while (delay.getCount()>0){
            delay.countDown();
        }
    }

    @Test
    public void testSaleServiceSuccess() throws Exception {

        saleResponse = null;
        errorResponse=null;
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
                if(response instanceof SaleService.SaleResponse)
                    saleResponse=(SaleService.SaleResponse) response;
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
        assertTrue(saleResponse.getStatus().contentEquals(SaleService.SaleResponse.PASS));
        assertTrue(saleResponse.getResponseCode().contentEquals("A0000"));
    }

    @Test
    public void testErrorExpirationDate() throws Exception {
        delay.await(6,TimeUnit.SECONDS);
        saleResponse = null;
        errorResponse=null;
        String transactionAmount = "0.10";
        String cardNumber = "5415920054179210";
        //This is a wrong in expiration date.
        String expirationDate = "xxxx";

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
                if(response instanceof SaleService.SaleResponse)
                    saleResponse=(SaleService.SaleResponse) response;
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
        assertTrue(saleResponse.getStatus().contentEquals(SaleService.SaleResponse.FAIL));
        assertTrue(saleResponse.getResponseCode().contentEquals("F9901"));

    }

    @Test
    public void testExpirationDateNullFails() throws Exception {
        delay.await(6,TimeUnit.SECONDS);
        saleResponse = null;
        errorResponse=null;
        String transactionAmount = "0.10";
        String cardNumber = "5415920054179210";
        //This is an invalid expiration date.
        String expirationDate = null;

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
                if(response instanceof SaleService.SaleResponse)
                    saleResponse=(SaleService.SaleResponse) response;
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
        assertTrue(saleResponse == null);
        assertTrue(errorResponse.getMsg().contains("must not"));
    }

    @Test
    public void testTransactionAmountEqualsZeroFails() throws Exception {
        delay.await(6,TimeUnit.SECONDS);
        saleResponse = null;
        errorResponse=null;
        //TransactionAmount equals 0.00
        String transactionAmount = "0.00";
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
                if(response instanceof SaleService.SaleResponse)
                    saleResponse=(SaleService.SaleResponse) response;
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
        assertTrue(saleResponse.getStatus().contentEquals(SaleService.SaleResponse.FAIL));
        assertTrue(saleResponse.getResponseCode().contentEquals("D2999"));
    }

    @Test
    public void testTransactionAmountNegativeFails() throws Exception {
        delay.await(6,TimeUnit.SECONDS);
        saleResponse = null;
        errorResponse=null;
        //TransactionAmount is negative
        String transactionAmount = "-0.10";
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
                if(response instanceof SaleService.SaleResponse)
                    saleResponse=(SaleService.SaleResponse) response;
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
        assertTrue(saleResponse.getStatus().contentEquals(SaleService.SaleResponse.FAIL));
        assertTrue(saleResponse.getResponseCode().contentEquals("F9901"));
    }

    @Test
    public void testThatInvalidCardNumberFails() throws Exception {
        delay.await(6,TimeUnit.SECONDS);
        saleResponse = null;
        errorResponse=null;
        String transactionAmount = "0.10";
        //Invalid cardNumber
        String cardNumber = "5415920054179210xx";
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
                if(response instanceof SaleService.SaleResponse)
                    saleResponse=(SaleService.SaleResponse) response;
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
        assertTrue(saleResponse.getStatus().contentEquals(SaleService.SaleResponse.FAIL));
        assertTrue(saleResponse.getResponseCode().contentEquals("E7260"));
    }

    @Test
    public void testThatNullCardNumberFails() throws Exception {
        delay.await(6,TimeUnit.SECONDS);
        saleResponse = null;
        errorResponse=null;
        String transactionAmount = "0.10";
        //Null cardNumber, empty cardNumber or filled with blanck spaces
        String cardNumber = "";
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
                if(response instanceof SaleService.SaleResponse)
                    saleResponse=(SaleService.SaleResponse) response;
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
        assertTrue(saleResponse == null);
        assertTrue(errorResponse.getMsg().contains("must not"));
    }

    @Test
    public void testThatNullTransactionAmountFails() throws Exception {
        delay.await(6,TimeUnit.SECONDS);
        saleResponse = null;
        errorResponse=null;
        //Null transactionAmount, empty or filled with black spaces
        String transactionAmount = "    ";
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
                if(response instanceof SaleService.SaleResponse)
                    saleResponse=(SaleService.SaleResponse) response;
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
        assertTrue(saleResponse == null);
        assertTrue(errorResponse.getMsg().contains("must not"));
    }


}
