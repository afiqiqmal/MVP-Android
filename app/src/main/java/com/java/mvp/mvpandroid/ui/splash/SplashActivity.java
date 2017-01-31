package com.java.mvp.mvpandroid.ui.splash;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.ui.common.BaseActivity;
import com.java.mvp.mvpandroid.utils.DeviceUtils;
import com.mvp.client.entity.response.TokenResponse;
import com.mvp.client.internal.Constant;

import javax.inject.Inject;

/**
 * Created by hafiq on 23/01/2017.
 */

public class SplashActivity extends BaseActivity implements SplashConnector {


    BroadcastReceiver tokenBroadcastReceiver;
    String token;
    String version;
    boolean isServiceFinish = false;

    @Inject
    SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activityGraph().inject(this);

        version = DeviceUtils.getDeviceVersion(this);
        mPresenter.setView(this);
        mPresenter.requestPushToken(this);

        analyticHelper.sendScreenName("SplashScreen");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.voidReceiver(this,tokenBroadcastReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.voidReceiver(this,tokenBroadcastReceiver);
    }

    @Override
    public void showContents(TokenResponse response) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void sendToken(String token) {
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

//        startActivity(new Intent(SplashActivity.this,MainActivity.class));

    }

    @Override
    public void sendBroatCast(BroadcastReceiver broadcastReceiver) {
        tokenBroadcastReceiver = broadcastReceiver;
        Log.d(Constant.TAG,"Masuk");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
