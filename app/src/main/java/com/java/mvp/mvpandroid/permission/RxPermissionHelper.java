package com.java.mvp.mvpandroid.permission;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

import com.java.mvp.mvpandroid.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * @author : hafiq on 28/07/2017.
 */

public class RxPermissionHelper {

    private Context context;
    private RxPermissions rxPermissions;

    public RxPermissionHelper(Context context) {
        this.context = context;
    }

    public void setRxPermissions(RxPermissions rxPermissions) {
        this.rxPermissions = rxPermissions;
    }

    public void requestPermissions(RxPermissionView.PermissionConnector connector, String... permission){
        check();
        rxPermissions
                .request(permission)
                .subscribe(connector::isPermissionGranted);
    }

    public void requestEachPermissions(RxPermissionView.MultiplePermissionConnector connector, String... permissions){
        check();
        rxPermissions
                .requestEach(permissions)
                .subscribe(permission -> {
                    if (permission.granted){
                        connector.isPermissionGranted(permission.name,true);
                    }
                    else if (permission.shouldShowRequestPermissionRationale){
                        connector.isPermissionShouldShowRationale(permission.name);
                    }
                    else{
                        connector.isPermissionGranted(permission.name,false);
                    }
                });
    }

    public void requestPermissionAgain(RxPermissionView.PermissionConnector connector, String... permissions){
        AlertDialog.Builder requestAgain = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        requestAgain.setCancelable(false);
        requestAgain.setTitle(R.string.permission_text);
        requestAgain.setMessage(R.string.permission_explain2_text);
        requestAgain.setPositiveButton(R.string.request_again_text, (dialog, which) -> requestPermissions(connector, permissions));
        requestAgain.setNegativeButton(R.string.no_text, (dialog, which) -> {dialog.dismiss();System.exit(0);});
        requestAgain.create().show();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showMessageRationale() {
        new AlertDialog.Builder(context,  R.style.AlertDialogCustom).setTitle(R.string.attention_text)
                .setCancelable(false)
                .setMessage(R.string.permission_explain_text)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void check() {
        if (rxPermissions == null) {
            throw new RuntimeException("RxPermissions need to be set first");
        }
    }
}
