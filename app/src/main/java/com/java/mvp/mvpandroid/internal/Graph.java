package com.java.mvp.mvpandroid.internal;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hafiq on 23/01/2017.
 */

@Singleton
@Component(modules = {
        AppModule.class,
})

public interface Graph {
    ActivityGraph activityGraph(ActivityModule module);
    ServiceGraph serviceGraph(ServiceModule module);
}


