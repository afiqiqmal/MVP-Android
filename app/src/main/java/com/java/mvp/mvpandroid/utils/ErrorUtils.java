package com.java.mvp.mvpandroid.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.mvp.client.internal.Constant;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by hafiq on 23/01/2017.
 */

@Singleton
public class ErrorUtils {

    private Context mContext;

    @Inject
    public ErrorUtils(Context context){
        this.mContext = context;
    }

    public void checkError(Throwable e){
        try {
            e.printStackTrace();
            firebaseReportError(e);

            if (e instanceof HttpException) {
                int code = getHttpErrorCode(e);
                httpMessage(code);
            } else if (e instanceof ConnectException || e instanceof SocketException || e instanceof SocketTimeoutException) {
                showToast("No Internet Connection Found");
            } else if (e instanceof UnknownHostException) {
                showToast("Make Sure Your Internet Connection is Properly");
            } else if (e instanceof SSLHandshakeException || e instanceof SSLPeerUnverifiedException) {
                showToast("Server Connection Problem. Please Try Again");
            } else {
                showErrorLog("Opps.. Something went wrong...");
            }
        }
        catch (Exception err){
            err.printStackTrace();
        }
    }

    public int getHttpErrorCode(Throwable e){
        Response body = ((HttpException) e).response();
        return body.code();
    }

    //only common http error
    private void httpMessage(int code){
        if (code == 400){
            showToast("Bad Request");
        }
        else if (code == 401) {
            showToast("Not Authorized Access");
        }
        else if (code == 403) {
            showToast("Forbidden Access");
        }
        else if (code == 404) {
            showToast("Data not Found");
        }
        else if (code == 405) {
            showToast("Request not allowed");
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
        if (mContext!=null) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showErrorLog(String message){
        Log.e(Constant.TAG,message);
    }

    public void firebaseReportError(Throwable e){
        FirebaseCrash.report(e);
    }
}
