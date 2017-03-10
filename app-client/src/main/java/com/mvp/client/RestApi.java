package com.mvp.client;

import com.mvp.client.entity.request.TokenRequest;
import com.mvp.client.entity.response.TokenResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hafiq on 23/01/2017.
 */

public interface RestApi {

    @POST("/api/token")
    Observable<TokenResponse> getToken(@Body TokenRequest request);

}
