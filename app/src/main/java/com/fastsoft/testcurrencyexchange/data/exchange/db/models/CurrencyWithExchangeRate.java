package com.fastsoft.testcurrencyexchange.data.exchange.db.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class CurrencyWithExchangeRate {
    @Embedded
    private CurrencySave currencySave;
    @Relation(parentColumn = "id", entityColumn = "currencySaveId",entity = ExchangeRateSave.class)
    private List<ExchangeRateSave> exchangeRateSaves;

    public CurrencyWithExchangeRate() {
    }

    public CurrencyWithExchangeRate(CurrencySave currencySave, List<ExchangeRateSave> exchangeRateSaves) {
        this.currencySave = currencySave;
        this.exchangeRateSaves = exchangeRateSaves;
    }
    public ExchangeRateSave findExchangeRateSaveByCurrency(String currency){
        for (ExchangeRateSave exchangeRateSave : exchangeRateSaves) {
            if(exchangeRateSave.getCurrency().equals(currency))
                return exchangeRateSave;
        }
        return null;
    }
    public CurrencySave getCurrencySave() {
        return currencySave;
    }

    public void setCurrencySave(CurrencySave currencySave) {
        this.currencySave = currencySave;
    }

    public List<ExchangeRateSave> getExchangeRateSaves() {
        return exchangeRateSaves;
    }

    public void setExchangeRateSaves(List<ExchangeRateSave> exchangeRateSaves) {
        this.exchangeRateSaves = exchangeRateSaves;
    }
}
