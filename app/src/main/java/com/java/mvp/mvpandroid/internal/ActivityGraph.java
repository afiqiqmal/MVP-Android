package com.java.mvp.mvpandroid.internal;
import com.java.mvp.mvpandroid.ui.common.BaseActivity;
import com.java.mvp.mvpandroid.ui.common.BaseFragment;
import com.java.mvp.mvpandroid.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
@Subcomponent(modules = {
        ActivityModule.class,
})

public interface ActivityGraph {

    //Activity
    void inject(BaseActivity activity);
    void inject(SplashActivity activity);


    // Fragment
    void inject(BaseFragment fragment);
}
