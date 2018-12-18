package com.fastsoft.testcurrencyexchange.data.exchange;

import com.fastsoft.testcurrencyexchange.data.exchange.db.models.CurrencyWithExchangeRate;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ExchengeRepository {
    Single<CurrencyWithExchangeRate> getCurrencyWithExchangeRate(String date);

    Observable<CurrencyWithExchangeRate> getCurrencyWithExchangeRate(List<String> dates);
}
