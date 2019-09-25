package com.nytimes.populararticles;

import android.app.Activity;
import android.app.Application;

import com.nytimes.populararticles.di.DaggerAppComponent;

import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Application class for the app
 */
public class NyTimeApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;
    private static NyTimeApp sInstance;

    public static NyTimeApp getAppContext() {
        return sInstance;
    }

    private static synchronized void setInstance(NyTimeApp app) {
        sInstance = app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        setInstance(this);
    }

    private void initializeComponent() {
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }
}
