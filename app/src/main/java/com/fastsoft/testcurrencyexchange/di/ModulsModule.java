package com.fastsoft.testcurrencyexchange.di;

import com.fastsoft.testcurrencyexchange.data.exchange.ExchengeRepositoryModule;
import com.fastsoft.testcurrencyexchange.data.exchange.api.ApiModule;

import dagger.Module;

@Module(includes = {ExchengeRepositoryModule.class,ApiModule.class,AppContextModule.class})
public interface ModulsModule {
}
