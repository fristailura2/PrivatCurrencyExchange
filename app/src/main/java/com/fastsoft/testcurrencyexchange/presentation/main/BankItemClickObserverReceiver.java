package com.fastsoft.testcurrencyexchange.presentation.main;

import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;

import io.reactivex.Observable;

public interface BankItemClickObserverReceiver {
    void onObservableReceive(Observable<ExchangeRateSave> observable);
}
