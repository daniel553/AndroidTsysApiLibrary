package com.ievolutioned.androidtsysapilibrary.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ievolutioned.androidtsysapilibrary.R;
import com.ievolutioned.androidtsysapilibrary.example.auth.AuthorizationActivity;
import com.ievolutioned.androidtsysapilibrary.example.util.ActivityUtil;

/**
 * Main examples for Tsys app library
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main_auth_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_main_auth_button:
                ActivityUtil.startActivity(MainActivity.this, AuthorizationActivity.class);
                break;
            default:
                break;
        }
    }
}
