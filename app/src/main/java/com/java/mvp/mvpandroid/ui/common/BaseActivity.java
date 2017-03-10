package com.java.mvp.mvpandroid.ui.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.java.mvp.mvpandroid.MVPApplication;
import com.java.mvp.mvpandroid.analytics.AnalyticHelper;
import com.java.mvp.mvpandroid.internal.ActivityGraph;
import com.java.mvp.mvpandroid.internal.ActivityModule;
import com.java.mvp.mvpandroid.internal.Graph;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by hafiq on 23/01/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected List<Disposable> mSubscriptions;

    @Inject
    protected PreferencesRepository preferencesRepository;

    @Inject
    protected ErrorUtils errorUtils;

    @Inject
    protected AnalyticHelper analyticHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(getApplication());

        activityGraph().inject(this);
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
        if (mSubscriptions == null) mSubscriptions = new ArrayList<>();
        mSubscriptions.add(s);
    }

    protected void unsubscribeAll() {
        if (mSubscriptions == null) return;
        for (Disposable s : mSubscriptions) {
            s.dispose();
        }
    }

}
