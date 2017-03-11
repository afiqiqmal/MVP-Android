package com.java.mvp.mvpandroid.ui.splash;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.permission.PermissionConnector;
import com.java.mvp.mvpandroid.ui.common.BaseActivity;
import com.java.mvp.mvpandroid.utils.DeviceUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mvp.client.entity.response.TokenResponse;
import com.mvp.client.internal.Constant;

import javax.inject.Inject;

/**
 * Created by hafiq on 23/01/2017.
 */

public class SplashActivity extends BaseActivity implements SplashConnector,PermissionConnector {


    BroadcastReceiver tokenBroadcastReceiver;
    String token;
    String version;
    boolean isServiceFinish = false;

    @Inject
    SplashPresenter mPresenter;

    private MultiplePermissionsListener allPermissionsListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activityGraph().inject(this);

        setPermission(this);
        initPermissionLister();
        Dexter.withActivity(this).continueRequestingPendingPermissions(allPermissionsListener);

        version = DeviceUtils.getDeviceVersion(this);
        mPresenter.setView(this);
        mPresenter.requestPushToken(this);

        analyticHelper.sendScreenName("SplashScreen");
    }

    private void initPermissionLister(){
        allPermissionsListener = new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener);
        setDexterMultiplePermissions(this,allPermissionsListener, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.GET_ACCOUNTS);
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

    @Override
    public void showPermissionGranted(String permissionName) {

    }

    @Override
    public void isAllPermissionGranted(boolean isAllGranted) {

    }

    @Override
    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {

    }

    @Override
    public void showPermissionRationale(PermissionRequest permissions, PermissionToken token) {

    }

    @Override
    public void showPermissionError(DexterError error) {

    }
}
