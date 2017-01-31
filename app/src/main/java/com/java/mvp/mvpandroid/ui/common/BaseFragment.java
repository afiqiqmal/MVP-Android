package com.java.mvp.mvpandroid.ui.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.java.mvp.mvpandroid.MVPApplication;
import com.java.mvp.mvpandroid.analytics.AnalyticHelper;
import com.java.mvp.mvpandroid.internal.ActivityGraph;
import com.java.mvp.mvpandroid.internal.Graph;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by hafiq on 23/01/2017.
 */

public class BaseFragment extends Fragment {

    protected List<Subscription> mSubscriptions;
    protected boolean mIsSubscriber = false;

    protected Unbinder unbinder;

    @Inject
    protected PreferencesRepository preferencesRepository;

    @Inject
    protected ErrorUtils errorUtils;

    @Inject
    protected AnalyticHelper analyticHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getActivity());

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

    protected void addSubscription(Subscription... s) {
        if (mSubscriptions == null) mSubscriptions = new ArrayList<>();
        if (s != null) Collections.addAll(mSubscriptions, s);
    }

    protected void unsubscribeAll() {
        try {
            if (mSubscriptions == null) return;
            for (Subscription s : mSubscriptions) {
                s.unsubscribe();
            }
        }
        catch (Exception ignored){}
    }
}
