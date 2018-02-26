package com.java.mvp.mvpandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.mvp.client.entity.response.ErrorResponse;
import com.mvp.client.internal.Constant;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
public class ErrorUtils {

    private Context mContext;
    private boolean LOG = false;
    private Throwable throwable;

    private PreferencesRepository preferencesRepository;

    @Inject
    public ErrorUtils(Context context, PreferencesRepository preferencesRepository){
        this.mContext = context;
        this.preferencesRepository = preferencesRepository;
    }

    public ErrorUtils(Context context){
        this.mContext = context;
    }

    public void checkError(Throwable e){
        try {
            throwable = e;
            e.printStackTrace();
            Crashlytics.logException(e);

            if (e instanceof HttpException) {
                int code = getHttpErrorCode(e);

                try {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    assert responseBody != null;
                    ErrorResponse response = new Gson().fromJson(responseBody.string(), ErrorResponse.class);
                    httpMessage(response.getMessage());

                    if (response.isShould_quit()) {
                        if(preferencesRepository != null) {
                            preferencesRepository.getPref().clearChamber();
                        }
                    }
                    else if (response.isShould_login()) {
                        if(preferencesRepository != null) {
                            preferencesRepository.getPref().clearChamber();
                        }
                    }

                } catch (Exception en) {
                    en.printStackTrace();
                    httpMessage(code);
                }

            } else if (e instanceof ConnectException) {
                showToast(mContext.getString(R.string.slow_internet));
            } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
                showToast(mContext.getString(R.string.internet_not_connected));
            } else if (e instanceof SSLHandshakeException || e instanceof SSLPeerUnverifiedException) {
                showToast(mContext.getString(R.string.server_problem));
            } else {
                showToast(mContext.getString(R.string.unknown_error_msg));
            }
        }
        catch (Exception err){
            err.printStackTrace();
        }
    }

    private int getHttpErrorCode(Throwable e){
        Response body = ((HttpException) e).response();
        return body.code();
    }

    private void httpMessage(String custom) {
        showToast(custom);
    }
    //only common http error
    private void httpMessage(int code){
        if (code == 400){
            showToast("Bad Request");
        }
        else if (code == 401) {
            showToast("No Authorize Access");
        }
        else if (code == 403) {
            showToast("Forbidden Access");
        }
        else if (code == 404) {
            showToast("Request Not Found");
        }
        else if (code == 405) {
            showToast("Request Not Allowed");
        }
        else if (code == 407){
            showToast("Proxy Authentication Required");
        }
        else if (code == 408){
            showToast("Data Request Expired");
        }
        else if (code == 500) {
            showToast("Internal Server Error Occurred");
        }
        else if (code == 502){
            showToast("Bad Url Gateway");
        }
        else if (code == 503){
            showToast("Service is Unavailable. Please Try Again");
        }
        else{
            showErrorLog("Error code : "+code);
        }
    }

    private void showToast(String message){
        if (!LOG) {
            if (mContext != null) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }
        }
        showErrorLog(message+": "+throwable.getMessage());
    }

    private void showErrorLog(String message){
        Log.e(Constant.ERROR_TAG,message);
    }

    public void recordError(Throwable e){
        try {
            e.printStackTrace();
            Crashlytics.logException(e);
            Crashlytics.log(e.getMessage());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
