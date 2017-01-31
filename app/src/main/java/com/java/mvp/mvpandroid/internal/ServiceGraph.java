package com.java.mvp.mvpandroid.internal;


import com.java.mvp.mvpandroid.services.RegistrationIntentService;

import dagger.Subcomponent;

/**
 * @author : hafiq on 23/01/2017.
 */

@PerService
@Subcomponent(modules = {
        ServiceModule.class,
})

public interface ServiceGraph {
    void inject(RegistrationIntentService service);
}