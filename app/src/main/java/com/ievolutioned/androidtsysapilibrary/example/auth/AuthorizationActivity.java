package com.ievolutioned.androidtsysapilibrary.example.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ievolutioned.androidtsysapilibrary.R;
import com.ievolutioned.tsysapilibrary.TsysApiLibrary;
import com.ievolutioned.tsysapilibrary.transit.BaseResponse;
import com.ievolutioned.tsysapilibrary.transit.CardDataSources;
import com.ievolutioned.tsysapilibrary.transit.TransitServiceCallback;
import com.ievolutioned.tsysapilibrary.transit.model.Auth;

/**
 * Authorization form activity. Contains the {@link com.ievolutioned.tsysapilibrary.transit.cardservices.AuthService}
 * service call and its response
 */
public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener, TransitServiceCallback {

    private EditText mDeviceId;
    private EditText mTransactionKey;
    //rivate EditText mCardSource;
    private String cardDataSource = CardDataSources.MANUAL;
    private EditText mTransactionAmount;
    private EditText mCardNumber;
    private EditText mExpirationDate;
    private TextView mResponseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        bindUI();
        setDemoData();
    }

    /**
     * Fill your data code behind
     */
    private void setDemoData() {
        mDeviceId.setText("");
        mTransactionKey.setText("");
        mTransactionAmount.setText("");
        mCardNumber.setText("");
        mExpirationDate.setText("");
    }

    /**
     * Binds ui components
     */
    private void bindUI() {
        mDeviceId = (EditText) findViewById(R.id.device_id);
        mTransactionKey = (EditText) findViewById(R.id.transaction_key);
        mTransactionAmount = (EditText) findViewById(R.id.transaction_amount);
        mCardNumber = (EditText) findViewById(R.id.card_number);
        mExpirationDate = (EditText) findViewById(R.id.expiration_date);
        mResponseText = (TextView) findViewById(R.id.response_text);

        findViewById(R.id.call_service_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_service_button:
                callService();
                break;
            default:
                break;
        }
    }

    /**
     * Calls the service
     */
    private void callService() {
        Auth auth = getAuthForm();
        TsysApiLibrary.doAuthorization(auth, this);
    }

    /**
     * Gets the {@link Auth} object from input
     *
     * @return an {@link Auth} object
     */
    private Auth getAuthForm() {
        return new Auth(mDeviceId.getText().toString(),
                mTransactionKey.getText().toString(),
                cardDataSource,
                mTransactionAmount.getText().toString(),
                mCardNumber.getText().toString(),
                mExpirationDate.getText().toString());
    }

    @Override
    public void onSuccess(String msg, BaseResponse response) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        mResponseText.setText(response.getMerchantReceipt());
    }

    @Override
    public void onError(String msg, BaseResponse response) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        mResponseText.setText(response.getResponseMessage());
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }
}
