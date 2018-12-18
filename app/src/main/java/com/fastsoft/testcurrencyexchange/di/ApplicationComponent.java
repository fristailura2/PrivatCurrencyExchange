package com.fastsoft.testcurrencyexchange.di;

import android.content.Context;

import com.fastsoft.testcurrencyexchange.App;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,ModulsModule.class, AndroidSupportInjectionModule.class, AndroidBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(App application);
        ApplicationComponent build();
    }



}
