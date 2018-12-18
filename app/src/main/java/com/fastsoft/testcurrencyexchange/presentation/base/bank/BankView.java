package com.fastsoft.testcurrencyexchange.presentation.base.bank;

import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.presentation.base.BaseView;

import java.util.List;

public interface BankView extends BaseView {
    void setExchangeRateSaves(List<ExchangeRateSave> exchangeRateSaves);

    void showDataPickerDialog();

    void showDate(String date);
}
