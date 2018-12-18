package com.fastsoft.testcurrencyexchange.data.exchange.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.fastsoft.testcurrencyexchange.data.exchange.db.models.CurrencySave;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.CurrencyWithExchangeRate;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;

import java.util.List;

@Dao
public abstract class CurrencyDao {
    @Transaction
    @Query("SELECT * FROM CurrencySave WHERE date=:date")
    public abstract CurrencyWithExchangeRate getExchangeRate(String date);
    @Insert
    protected abstract long insert(CurrencySave currencySave);
    @Insert
    protected abstract void insert(List<ExchangeRateSave> currencySave);
    @Transaction
    public void insert(CurrencyWithExchangeRate currencyWithExchangeRate){
        long id=insert(currencyWithExchangeRate.getCurrencySave());
        for (ExchangeRateSave exchangeRateSave : currencyWithExchangeRate.getExchangeRateSaves())
            exchangeRateSave.setCurrencySaveId(id);
        insert(currencyWithExchangeRate.getExchangeRateSaves());
    }

}
