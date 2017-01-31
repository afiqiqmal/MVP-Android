package com.java.mvp.mvpandroid.ui.splash;

import android.content.BroadcastReceiver;

import com.mvp.client.entity.response.TokenResponse;

/**
 * @author : hafiq on 23/01/2017.
 */

public interface SplashConnector {

    void showContents(TokenResponse response);
    void showLoading();
    void showError(Throwable throwable);
    void sendToken(String token);
    void sendBroatCast(BroadcastReceiver broadcastReceiver);
}
