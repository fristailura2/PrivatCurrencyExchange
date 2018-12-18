package com.fastsoft.testcurrencyexchange.presentation.main.privat;

import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.presentation.base.BaseView;
import com.fastsoft.testcurrencyexchange.presentation.base.bank.BankView;

import java.util.List;

import io.reactivex.Observable;

public interface PrivateView extends BankView {
    void sendClickObservableUp();

    Observable<ExchangeRateSave> getClickObservable();
}
