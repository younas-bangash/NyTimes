package com.nytimes.populararticles.di;

import com.nytimes.populararticles.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * The module which provides the android injection service to Activities.
 */
@SuppressWarnings("unused")
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity contributeMainActivity();
}
