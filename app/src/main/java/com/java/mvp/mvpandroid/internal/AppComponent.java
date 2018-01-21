package com.java.mvp.mvpandroid.internal;


import com.java.mvp.mvpandroid.internal.activity.ActivityComponent;
import com.java.mvp.mvpandroid.internal.activity.ActivityModule;
import com.java.mvp.mvpandroid.internal.fragment.FragmentComponent;
import com.java.mvp.mvpandroid.internal.fragment.FragmentModule;
import com.java.mvp.mvpandroid.internal.network.ApiModule;
import com.java.mvp.mvpandroid.internal.network.BaseOkHttpClient;
import com.java.mvp.mvpandroid.internal.service.ServiceComponent;
import com.java.mvp.mvpandroid.internal.service.ServiceModule;
import com.java.mvp.mvpandroid.permission.RxPermissionModule;
import com.java.mvp.mvpandroid.repository.PreferenceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        PreferenceModule.class,
        RxPermissionModule.class,
        BaseOkHttpClient.class,
        ApiModule.class,
})

public interface AppComponent {
    ActivityComponent activityComponent(ActivityModule module);
    FragmentComponent fragmentComponent(FragmentModule module);
    ServiceComponent serviceComponent(ServiceModule module);
}


