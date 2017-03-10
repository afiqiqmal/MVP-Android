package com.java.mvp.mvpandroid.permission;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;

import javax.inject.Inject;

/**
 * @author : hafiq on 05/03/2017.
 */

public class BackgroundPermissionListener extends RequestSinglePermissionListener {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Inject
    public BackgroundPermissionListener(Activity activity) {
        super(activity);
    }

    @Override
    public void onPermissionGranted(final PermissionGrantedResponse response) {
        handler.post(() -> BackgroundPermissionListener.super.onPermissionGranted(response));
    }

    @Override
    public void onPermissionDenied(final PermissionDeniedResponse response) {
        handler.post(() -> BackgroundPermissionListener.super.onPermissionDenied(response));
    }

    @Override
    public void onPermissionRationaleShouldBeShown(final PermissionRequest permission, final PermissionToken token) {
        handler.post(() -> BackgroundPermissionListener.super.onPermissionRationaleShouldBeShown(permission, token));
    }
}
