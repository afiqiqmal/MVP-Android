package com.java.mvp.mvpandroid.utils;

import android.app.Activity;
import android.app.ProgressDialog;

import javax.inject.Inject;

/**
 * @author : hafiq on 06/03/2017.
 */

public class ProgressDialogUtils {
    Activity activity;

    ProgressDialog progressDialog;

    @Inject
    public ProgressDialogUtils(Activity activity) {
        this.activity = activity;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setStyle(int style){
        if (progressDialog!=null)
            progressDialog.setProgressStyle(style);
    }

    public void setProgress(int value){
        if (progressDialog!=null) {
            progressDialog.setProgress(value);
        }
    }

    public void setProgress(int value,int max){
        if (progressDialog!=null) {
            progressDialog.setProgress(value);
            progressDialog.setMax(max);
        }
    }

    public void setCancelable(boolean enable){
        if (progressDialog!=null) {
            progressDialog.setCancelable(enable);
        }
    }

    public void openProgress(){
        activity.runOnUiThread(() -> {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading..");
            progressDialog.show();
        });
    }

    public void openProgress(String message){
        activity.runOnUiThread(() -> {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(message);
            progressDialog.show();
        });
    }

    public void openProgress(int message){
        activity.runOnUiThread(() -> {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(activity.getString(message));
            progressDialog.show();
        });
    }

    public void setMessage(String message){
        if (progressDialog!=null) {
            activity.runOnUiThread(() -> {
                if (progressDialog != null) {
                    progressDialog.setMessage(message);
                }
            });
        }
    }

    public void setMessage(int message){
        if (progressDialog!=null) {
            activity.runOnUiThread(() -> {
                if (progressDialog != null) {
                    progressDialog.setMessage(activity.getString(message));
                }
            });
        }
    }

    public void closeProgress(){
        if (progressDialog!=null) {
            activity.runOnUiThread(() -> {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            });
        }
    }
}
