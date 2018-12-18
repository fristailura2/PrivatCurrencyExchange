package com.fastsoft.testcurrencyexchange.data.exchange.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.fastsoft.testcurrencyexchange.data.exchange.db.models.CurrencySave;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
@TypeConverters(DataConverter.class)
@Database(version = 8,entities = {CurrencySave.class,ExchangeRateSave.class})
public abstract class CurrencyDB extends RoomDatabase {
    public abstract CurrencyDao getCurrencyDao();
}
