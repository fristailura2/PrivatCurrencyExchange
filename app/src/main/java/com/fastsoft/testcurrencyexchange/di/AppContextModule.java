package com.fastsoft.testcurrencyexchange.di;

import android.content.Context;

import com.fastsoft.testcurrencyexchange.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppContextModule {
    @Provides
    @Singleton
    public Context provideAppConext(App app){
        return app.getApplicationContext();
    }
}
