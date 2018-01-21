package com.java.mvp.mvpandroid.internal.fragment;



import com.java.mvp.mvpandroid.internal.activity.PerActivity;
import com.java.mvp.mvpandroid.ui.common.BaseFragment;

import dagger.Subcomponent;

/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
@Subcomponent(modules = {
        FragmentModule.class,
})

public interface FragmentComponent {

    //Fragment
    void inject(BaseFragment fragment);

}
