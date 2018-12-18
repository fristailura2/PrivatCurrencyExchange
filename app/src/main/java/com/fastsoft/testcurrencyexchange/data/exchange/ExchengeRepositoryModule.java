package com.fastsoft.testcurrencyexchange.data.exchange;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.fastsoft.testcurrencyexchange.data.exchange.api.PrivateExchangeApi;
import com.fastsoft.testcurrencyexchange.data.exchange.db.CurrencyDB;
import com.fastsoft.testcurrencyexchange.data.exchange.db.CurrencyDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ExchengeRepositoryModule {
    @Provides
    @Singleton
    public ExchengeRepository provideExchengeRepository(CurrencyDao currencyDao, PrivateExchangeApi privateExchangeApi){
        return new ExchangeRepositoryImpl(currencyDao, privateExchangeApi);
    }
    @Provides
    @Singleton
    public CurrencyDao provideCurrencyDao(CurrencyDB currencyDB){
        return currencyDB.getCurrencyDao();
    }
    @Provides
    @Singleton
    public CurrencyDB provideCurrencyDB(Context context){
        return Room.databaseBuilder(context,CurrencyDB.class,CurrencyDB.class.getSimpleName())
                .fallbackToDestructiveMigration()
                .build();
    }
}
