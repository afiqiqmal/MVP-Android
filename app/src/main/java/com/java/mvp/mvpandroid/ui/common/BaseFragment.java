package com.java.mvp.mvpandroid.ui.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.MVPApplication;
import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.analytics.AnalyticHelper;
import com.java.mvp.mvpandroid.internal.ActivityGraph;
import com.java.mvp.mvpandroid.internal.Graph;
import com.java.mvp.mvpandroid.permission.PermissionConnector;
import com.java.mvp.mvpandroid.permission.RequestMultiplePermissionListener;
import com.java.mvp.mvpandroid.permission.RequestSinglePermissionListener;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.utils.ErrorUtils;
import com.java.mvp.mvpandroid.utils.ProgressDialogUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.logger.min.easylogger.Logger;
import com.mvp.client.internal.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * Created by hafiq on 23/01/2017.
 */

public class BaseFragment extends Fragment {

    protected List<Disposable> mSubscriptions;

    protected Unbinder unbinder;

    @Inject
    protected PreferencesRepository preferencesRepository;

    @Inject
    protected ErrorUtils errorUtils;

    @Inject
    protected AnalyticHelper analyticHelper;

    @Inject
    protected ProgressDialogUtils progress;

    @Inject
    protected RequestSinglePermissionListener feedbackViewPermissionListener;

    @Inject
    protected RequestMultiplePermissionListener feedbackViewMultiplePermissionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Logger.Builder(getActivity()).setTag(Constant.TAG).enableLog(BuildConfig.DEBUG).create();

        activityGraph().inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
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

    protected void addSubscription(Disposable... s) {
        if (mSubscriptions == null) mSubscriptions = new ArrayList<>();
        if (s != null) Collections.addAll(mSubscriptions, s);
    }

    protected void unsubscribeAll() {
        try {
            if (mSubscriptions == null) return;
            for (Disposable s : mSubscriptions) {
                s.dispose();
            }
        }
        catch (Exception ignored){}
    }

    public void setPermission(PermissionConnector connector){
        feedbackViewMultiplePermissionListener.setConnector(connector);
        feedbackViewPermissionListener.setConnector(connector);
    }

    public void setDexterMultiplePermissions(Activity activity, MultiplePermissionsListener permissionsListener, String... permissions){
        Dexter.withActivity(activity)
                .withPermissions(permissions)
                .withListener(permissionsListener)
                .check();
    }

    public void setDexterPermission(Activity activity, PermissionListener permissionsListener, String permissions){
        Dexter.withActivity(activity)
                .withPermission(permissions)
                .withListener(permissionsListener)
                .check();
    }

    public DialogOnDeniedPermissionListener dialogPermission(Activity activity, String title, String message){
        return DialogOnDeniedPermissionListener.Builder.withContext(activity)
                .withTitle(title)
                .withMessage(message)
                .withButtonText(android.R.string.ok)
                .withIcon(R.mipmap.ic_launcher)
                .build();
    }

    public DialogOnAnyDeniedMultiplePermissionsListener dialogMultiplePermission(Activity activity, String title, String message){
        return DialogOnAnyDeniedMultiplePermissionsListener.Builder.withContext(activity)
                .withTitle(title)
                .withMessage(message)
                .withButtonText(android.R.string.ok)
                .withIcon(R.mipmap.ic_launcher)
                .build();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showMessageRationale(final PermissionToken token) {
        new AlertDialog.Builder(getActivity()).setTitle("Attention")
                .setCancelable(false)
                .setMessage("This permission is needed for doing fancy stuff, so please, allow it!!!")
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                    token.cancelPermissionRequest();
                })
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                    token.continuePermissionRequest();
                })
                .setOnDismissListener(dialog -> token.cancelPermissionRequest())
                .show();
    }
}
