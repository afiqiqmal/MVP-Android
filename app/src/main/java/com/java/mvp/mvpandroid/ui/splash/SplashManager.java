package com.java.mvp.mvpandroid.ui.splash;

import com.java.mvp.mvpandroid.ui.common.mvp.BaseManager;
import com.mvp.client.internal.RestApi;
import com.mvp.client.entity.request.TokenRequest;
import com.mvp.client.entity.response.TokenResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
public class SplashManager extends BaseManager {

    @Inject
    public SplashManager(RestApi api) {
        restApi = api;
    }

    public Observable<TokenResponse> getToken(TokenRequest request) {
        return restApi.getToken(request);
    }
}

