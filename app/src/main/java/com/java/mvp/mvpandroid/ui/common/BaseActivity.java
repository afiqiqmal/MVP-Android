package com.java.mvp.mvpandroid.ui.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.MVPApplication;
import com.java.mvp.mvpandroid.analytics.AnalyticHelper;
import com.java.mvp.mvpandroid.internal.ActivityGraph;
import com.java.mvp.mvpandroid.internal.ActivityModule;
import com.java.mvp.mvpandroid.internal.Graph;
import com.java.mvp.mvpandroid.permission.MultiplePermissionConnector;
import com.java.mvp.mvpandroid.permission.PermissionConnector;
import com.java.mvp.mvpandroid.repository.ConcealRepository;
import com.java.mvp.mvpandroid.utils.DeviceUtils;
import com.java.mvp.mvpandroid.utils.ErrorUtils;
import com.java.mvp.mvpandroid.utils.ProgressDialogUtils;
import com.logger.min.easylogger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author : hafiq on 23/01/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable mSubscriptions;

    @Inject
    protected ConcealRepository preferencesRepository;

    @Inject
    protected ErrorUtils errorUtils;

    @Inject
    protected AnalyticHelper analyticHelper;

    @Inject
    protected ProgressDialogUtils progress;

    RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Logger.Builder(this).setTag("TAG").enableLog(BuildConfig.DEBUG).create();

        activityGraph().inject(this);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        DeviceUtils.closeSoftKeyboard(this);

        rxPermissions = new RxPermissions(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        unsubscribeAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public Graph getGraph() {
        return MVPApplication.graph(this);
    }

    protected ActivityGraph activityGraph() {
        return getGraph().activityGraph(new ActivityModule(this));
    }

    protected void addSubscription(Disposable s) {
        if (mSubscriptions == null) mSubscriptions = new CompositeDisposable();
        mSubscriptions.add(s);
    }

    protected void unsubscribeAll() {
        if (mSubscriptions == null) return;
        mSubscriptions.clear();
    }

    public void requestPermissions(PermissionConnector connector,String... permission){
        rxPermissions
                .request(permission)
                .subscribe((granted) -> {
                    connector.isPermissionGranted(granted);
                });
    }

    public void requestEachPermissions(MultiplePermissionConnector connector, String... permissions){
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

    public void requestPermissionAgain(PermissionConnector connector, String... permissions){
        AlertDialog.Builder requestAgain = new AlertDialog.Builder(this);
        requestAgain.setCancelable(false);
        requestAgain.setTitle("Permission");
        requestAgain.setMessage("You are not able to proceed if you not allow those permission");
        requestAgain.setPositiveButton("Request Again", (dialog, which) -> requestPermissions(connector, permissions));
        requestAgain.setNegativeButton("Cancel", (dialog, which) -> {dialog.dismiss();System.exit(0);});
        requestAgain.create().show();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showMessageRationale() {
        new AlertDialog.Builder(this).setTitle("Attention")
                .setCancelable(false)
                .setMessage("This permissions is needed for doing fancy stuff, so please, allow it!!!")
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

}
