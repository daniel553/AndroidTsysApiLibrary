package com.ievolutioned.androidtsysapilibrary;

import android.support.test.runner.AndroidJUnit4;

import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.CardDataSources;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.cardservices.SaleService;
import com.ievolutioned.tsysapilibrary.transit.model.Sale;

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

    private SaleService.SaleResponse saleResponse = null;
    private String message;
    private String deviceId="88300000228401";
    private String transactionKey="1SN6NMT7MI3XQ8SSJSL592DAHNVGCQC0";
    private String cardDataSource= CardDataSources.MANUAL;

    @Test
    public void testSaleServiceSuccess() throws Exception {
        saleResponse = null;
        String transactionAmount = "0.10";
        String cardNumber = "5415920054179210";
        String expirationDate = "0819";

        Sale sale = new Sale(deviceId, transactionKey, cardDataSource, transactionAmount, cardNumber, expirationDate);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new SaleService(sale, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                countDownLatch.countDown();
                saleResponse = (SaleService.SaleResponse) response;
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
        assertTrue(saleResponse.getStatus().contentEquals(SaleService.SaleResponse.PASS));
        assertTrue(saleResponse.getResponseCode().contentEquals("A0000"));
    }

    @Test
    public void testErrorExpirationDate() throws Exception {
        saleResponse = null;
        String transactionAmount = "0.10";
        String cardNumber = "5415920054179210";
        //This is a wrong in expiration date.
        String expirationDate = "xxxx";

        Sale sale = new Sale(deviceId, transactionKey, cardDataSource, transactionAmount, cardNumber, expirationDate);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new SaleService(sale, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                countDownLatch.countDown();
                saleResponse = (SaleService.SaleResponse) response;
            }

            @Override
            public void onError(String msg, BaseResponse response) {
                countDownLatch.countDown();
                saleResponse = (SaleService.SaleResponse) response;
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
    public void testExpirationDateNullFails() throws Exception{
        saleResponse = null;
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
                message=msg;
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
        assertTrue(saleResponse==null);
        assertTrue(message.contains("must not"));
    }

    @Test
    public void testTransactionAmountEqualsZeroFails() throws Exception{
        saleResponse = null;
        //TransactionAmount equals 0.00
        String transactionAmount = "0.00";
        String cardNumber = "5415920054179210";
        String expirationDate = "0819";

        Sale sale = new Sale(deviceId, transactionKey, cardDataSource, transactionAmount, cardNumber, expirationDate);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new SaleService(sale, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                countDownLatch.countDown();
                saleResponse = (SaleService.SaleResponse) response;
            }

            @Override
            public void onError(String msg, BaseResponse response) {
                countDownLatch.countDown();
                saleResponse = (SaleService.SaleResponse) response;
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
    public void testTransactionAmountNegativeFails() throws Exception{
        saleResponse = null;
        //TransactionAmount is negative
        String transactionAmount = "-0.10";
        String cardNumber = "5415920054179210";
        String expirationDate = "0819";

        Sale sale = new Sale(deviceId, transactionKey, cardDataSource, transactionAmount, cardNumber, expirationDate);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new SaleService(sale, new TransitServiceCallback() {
            @Override
            public void onSuccess(String msg, BaseResponse response) {
                countDownLatch.countDown();
                saleResponse = (SaleService.SaleResponse) response;
            }

            @Override
            public void onError(String msg, BaseResponse response) {
                countDownLatch.countDown();
                saleResponse = (SaleService.SaleResponse) response;
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
    public void testThatInvalidCardNumberFails() throws Exception{
       saleResponse = null;
       String transactionAmount = "0.10";
       //Invalid cardNumber
       String cardNumber = "5415920054179210xx";
       String expirationDate = "0819";

       Sale sale = new Sale(deviceId, transactionKey, cardDataSource, transactionAmount, cardNumber, expirationDate);

       final CountDownLatch countDownLatch = new CountDownLatch(1);
       new SaleService(sale, new TransitServiceCallback() {
           @Override
           public void onSuccess(String msg, BaseResponse response) {
               countDownLatch.countDown();
               saleResponse = (SaleService.SaleResponse) response;
           }

           @Override
           public void onError(String msg, BaseResponse response) {
               countDownLatch.countDown();
               saleResponse = (SaleService.SaleResponse) response;
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
    public void testThatNullCardNumberFails() throws Exception{
        saleResponse = null;
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
                message=msg;
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
        assertTrue(saleResponse==null);
        assertTrue(message.contains("must not"));
    }

    @Test
    public void testThatNullTransactionAmountFails() throws Exception{
        saleResponse = null;
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
                message=msg;
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
        assertTrue(saleResponse==null);
        assertTrue(message.contains("must not"));
    }




}
