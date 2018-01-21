package com.java.mvp.mvpandroid.ui.splash;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.analytics.Screen;
import com.java.mvp.mvpandroid.ui.MainActivity;
import com.java.mvp.mvpandroid.ui.common.BaseActivity;
import com.java.mvp.mvpandroid.utils.CommonUtils;
import com.java.mvp.mvpandroid.utils.DeviceUtils;
import com.mvp.client.entity.request.TokenRequest;
import com.mvp.client.entity.response.TokenResponse;
import com.mvp.client.internal.Constant;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author : hafiq on 23/01/2017.
 */

public class SplashActivity extends BaseActivity implements SplashConnector {


    BroadcastReceiver tokenBroadcastReceiver;
    String version;
    boolean isTimerFinished = false;
    boolean isTokenFinished = false;

    @Inject
    SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activityComponent().inject(this);

        if (CommonUtils.checkPlayServices(this)) {
            version = DeviceUtils.getDeviceVersion(this);

            runProcess();

        } else {
            Toast.makeText(this, R.string.google_service_update, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void runProcess() {
        // run timer for 3 seconds to mask process
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(timer->{
                    isTimerFinished = true;
                    isTokenFinished = true;

                    checkFinished();
                },t->{
                    errorUtils.recordError(t);
                });

//        if (preferencesRepository.getPushToken() == null)
//            mPresenter.requestPushToken(this);
//        else {
//            mPresenter.getToken(requestDetail());
//        }
    }

    private TokenRequest requestDetail() {
        return new TokenRequest(
                DeviceUtils.getUDID(this),
                getString(R.string.mobile_os),
                DeviceUtils.getAPIlevel(),
                DeviceUtils.getDeviceVersion(this),
                preferencesRepository.getPushToken()
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.voidReceiver(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.voidReceiver(this);
    }

    @Override
    public void showContents(TokenResponse response) {
        isTokenFinished = true;
        checkFinished();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(Throwable throwable) {
        errorUtils.checkError(throwable);
        try {
            //
        } catch (Exception e) {
            errorUtils.recordError(e);
        }
    }

    @Override
    public void sendToken(String token) {
        if (token != null) {
            mPresenter.getToken(requestDetail());
        } else {
            errorUtils.checkError(new RuntimeException("Token is Null"));
        }
    }

    private void checkFinished() {
        if (isTimerFinished && isTokenFinished) {
            Intent intent;
            intent = new Intent(new Intent(SplashActivity.this, MainActivity.class));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }

    @Override
    public void sendBroadCast(BroadcastReceiver broadcastReceiver) {
        tokenBroadcastReceiver = broadcastReceiver;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        analyticHelper.sendScreenName(Screen.SPLASHSCREEN);

    }
}
