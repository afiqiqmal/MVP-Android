package com.java.mvp.mvpandroid.ui.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.MVPApplication;
import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.analytics.AnalyticHelper;
import com.java.mvp.mvpandroid.internal.ActivityGraph;
import com.java.mvp.mvpandroid.internal.Graph;
import com.java.mvp.mvpandroid.permission.MultiplePermissionConnector;
import com.java.mvp.mvpandroid.permission.PermissionConnector;
import com.java.mvp.mvpandroid.repository.ConcealRepository;
import com.java.mvp.mvpandroid.utils.DeviceUtils;
import com.java.mvp.mvpandroid.utils.ErrorUtils;
import com.java.mvp.mvpandroid.utils.ProgressDialogUtils;
import com.logger.min.easylogger.Logger;
import com.mvp.client.internal.Constant;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : hafiq on 23/01/2017.
 */

public class BaseFragment extends Fragment {

    protected CompositeDisposable mSubscriptions;
    protected boolean mIsSubscriber = false;

    protected Unbinder unbinder;

    @Inject
    protected ConcealRepository preferencesRepository;

    @Inject
    protected ErrorUtils errorUtils;

    @Inject
    protected AnalyticHelper analyticHelper;

    @Inject
    protected ProgressDialogUtils progress;

    public RxPermissions rxPermissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Logger.Builder(getActivity()).setTag("LOG").enableLog(BuildConfig.DEBUG).create();

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        activityGraph().inject(this);

        rxPermissions = new RxPermissions(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        DeviceUtils.closeSoftKeyboard(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();

        unsubscribeAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null)
            unbinder.unbind();
    }

    public Graph getGraph() {
        return MVPApplication.graph(getActivity());
    }

    protected ActivityGraph activityGraph() {
        return ((BaseActivity) getActivity()).activityGraph();
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
                .subscribe(connector::isPermissionGranted);
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
        android.support.v7.app.AlertDialog.Builder requestAgain = new android.support.v7.app.AlertDialog.Builder(getActivity());
        requestAgain.setCancelable(false);
        requestAgain.setTitle("Permission");
        requestAgain.setMessage("You are not able to proceed if you not allow those permission");
        requestAgain.setPositiveButton("Request Again", (dialog, which) -> requestPermissions(connector, permissions));
        requestAgain.setNegativeButton("Cancel", (dialog, which) -> {dialog.dismiss();System.exit(0);});
        requestAgain.create().show();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showMessageRationale() {
        new AlertDialog.Builder(getActivity()).setTitle("Attention")
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
