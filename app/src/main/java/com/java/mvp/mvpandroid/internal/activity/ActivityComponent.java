package com.java.mvp.mvpandroid.internal.activity;

import com.java.mvp.mvpandroid.ui.MainActivity;
import com.java.mvp.mvpandroid.ui.common.BaseActivity;
import com.java.mvp.mvpandroid.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
@Subcomponent(modules = {
        ActivityModule.class,
})

public interface ActivityComponent {

    //Activity
    void inject(MainActivity activity);
    void inject(BaseActivity activity);
    void inject(SplashActivity activity);

}
