package com.java.mvp.mvpandroid.ui.splash;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.services.RegistrationIntentService;

import com.java.mvp.mvpandroid.ui.common.mvp.BasePresenter;
import com.mvp.client.entity.request.TokenRequest;
import com.mvp.client.entity.response.TokenResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : hafiq on 23/01/2017.
 */

public class SplashPresenter extends BasePresenter{

    private final SplashManager manager;
    private final PreferencesRepository preferences;
    private SplashConnector mView;
    private final CompositeDisposable mSubscription = new CompositeDisposable();

    private TokenBroadCastService tokenBroadcastReceiver;

    @Inject
    SplashPresenter(SplashManager manager, PreferencesRepository preferences) {
        this.manager = manager;
        this.preferences = preferences;
        tokenBroadcastReceiver = new TokenBroadCastService();
    }

    public void getToken(TokenRequest request) {
        Disposable s = manager.getToken(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> showLoading())
                .subscribe(tokenResponse -> {
                    preferences.setAuthToken(tokenResponse.getToken());
                    showContents(tokenResponse);
                }, throwable -> {
                    throwable.printStackTrace();
                    showError(throwable);
                });
        mSubscription.add(s);
    }

    void requestPushToken(Activity activity){
        if (activity!=null) {
            Intent service = new Intent(activity, RegistrationIntentService.class);
            service.putExtra(RegistrationIntentService.TOKEN, "token");
            activity.startService(service);

            IntentFilter intentFilter = new IntentFilter(RegistrationIntentService.ACTION_TOKEN);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            activity.registerReceiver(tokenBroadcastReceiver, intentFilter);

            //send back first
            sendBroadCast();
        }
        else{

        }
    }

    public class TokenBroadCastService extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra(RegistrationIntentService.TOKEN);
            sendToken(token);
        }
    }

    void voidReceiver(Activity activity){
        try {
            activity.unregisterReceiver(tokenBroadcastReceiver);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendBroadCast(){
        if (mView == null) return;
        mView.sendBroadCast(tokenBroadcastReceiver);
    }

    private void sendToken(String token){
        if (mView == null) return;
        mView.sendToken(token);
    }

    @Override
    public void showLoading() {
        if (mView == null) return;
        mView.showLoading();
    }

    @Override
    public void showContents(Object response) {
        if (mView == null) return;
        mView.showContents((TokenResponse)response);
    }

    @Override
    public void showError(Object error) {
        if (mView == null) return;
        mView.showError((Throwable) error);
    }

    @Override
    public void setView(Object view) {
        mView = (SplashConnector) view;
        if (view == null) mSubscription.clear();
    }
}