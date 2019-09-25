package com.nytimes.populararticles.di;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * ViewModel Key which serves as the unique key for keeping the viewmodel instances in the factory
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
@interface ViewModelKey {
    @SuppressWarnings("unused")
    Class<? extends ViewModel> value();
}