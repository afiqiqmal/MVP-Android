package com.java.mvp.mvpandroid.internal.service;


import com.java.mvp.mvpandroid.services.RegistrationIntentService;

import dagger.Subcomponent;


/**
 * @author : hafiq on 23/01/2017.
 */

@PerService
@Subcomponent(modules = {
        ServiceModule.class,
})

public interface ServiceComponent {
    void inject(RegistrationIntentService service);
}