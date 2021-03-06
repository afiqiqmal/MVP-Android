package com.java.mvp.mvpandroid.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.permission.RxPermissionView;
import com.java.mvp.mvpandroid.ui.common.BaseActivity;

/**
 * @author : hafiq on 29/05/2017.
 */

public class MainActivity extends BaseActivity implements RxPermissionView.PermissionConnector {

    String permissions[] = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityComponent().inject(this);

        rxPermissions.requestPermissions(this, permissions);
    }

    private void initMethod(){

    }

    @Override
    public void isPermissionGranted(boolean isAllGranted) {
        if (isAllGranted){
            initMethod();
        }
        else{
            rxPermissions.requestPermissionAgain(this, permissions);
        }
    }
}
