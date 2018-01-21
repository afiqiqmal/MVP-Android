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
import com.java.mvp.mvpandroid.analytics.AnalyticHelper;
import com.java.mvp.mvpandroid.internal.AppComponent;
import com.java.mvp.mvpandroid.internal.fragment.FragmentComponent;
import com.java.mvp.mvpandroid.internal.fragment.FragmentModule;
import com.java.mvp.mvpandroid.permission.RxPermissionHelper;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.utils.DeviceUtils;
import com.java.mvp.mvpandroid.utils.ErrorUtils;
import com.java.mvp.mvpandroid.utils.ProgressDialogUtils;
import com.java.mvp.mvpandroid.utils.TypeFaceUtils;
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
    protected PreferencesRepository preferencesRepository;

    @Inject
    protected ErrorUtils errorUtils;

    @Inject
    protected AnalyticHelper analyticHelper;

    @Inject
    protected TypeFaceUtils typeFaceUtils;

    Activity activity;

    @Inject
    protected RxPermissionHelper rxPermissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        fragmentComponent().inject(this);
        rxPermissions.setRxPermissions(new RxPermissions(getActivity()));
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
        if (unbinder != null)
            unbinder.unbind();
    }

    public AppComponent getAppComponent() {
        return MVPApplication.daggerAppComponent(getActivity());
    }


    protected FragmentComponent fragmentComponent() {
        return getAppComponent().fragmentComponent(new FragmentModule(getActivity()));
    }


    protected void addSubscription(Disposable s) {
        if (mSubscriptions == null) mSubscriptions = new CompositeDisposable();
        mSubscriptions.add(s);
    }

    protected void unsubscribeAll() {
        if (mSubscriptions == null) return;
        mSubscriptions.clear();
    }
}
