package com.fastsoft.testcurrencyexchange.presentation.main.nbu;

import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.presentation.base.BaseView;
import com.fastsoft.testcurrencyexchange.presentation.base.bank.BankView;

import java.util.List;

public interface NBUView extends BankView {
    void scrollTo(ExchangeRateSave exchangeRateSave);
}
