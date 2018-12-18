package com.fastsoft.testcurrencyexchange;

import android.app.Application;

import com.fastsoft.testcurrencyexchange.di.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder()
                .application(this)
                .build();
    }
}
