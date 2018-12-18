package com.fastsoft.testcurrencyexchange.data.exchange;

import com.fastsoft.testcurrencyexchange.data.exchange.api.ExchangeRate;
import com.fastsoft.testcurrencyexchange.data.exchange.api.PrivateApiResponse;
import com.fastsoft.testcurrencyexchange.data.exchange.api.PrivateExchangeApi;
import com.fastsoft.testcurrencyexchange.data.exchange.db.CurrencyDao;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.CurrencySave;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.CurrencyWithExchangeRate;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.utils.Optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class ExchangeRepositoryImpl implements ExchengeRepository{
    private CurrencyDao currencyDao;
    private PrivateExchangeApi currencyApi;

    public ExchangeRepositoryImpl(CurrencyDao currencyDao, PrivateExchangeApi currencyApi) {
        this.currencyDao = currencyDao;
        this.currencyApi = currencyApi;
    }

    @Override
    public Single<CurrencyWithExchangeRate> getCurrencyWithExchangeRate(String date) {
        return getCurrencyWithExchangeRate(Arrays.asList(date)).firstOrError();
    }
    @Override
    public Observable<CurrencyWithExchangeRate> getCurrencyWithExchangeRate(List<String> dates) {
        List<Single<CurrencyWithExchangeRate>> requests=new ArrayList<>();
        for (String currentRequestDate : dates) {
            Single<CurrencyWithExchangeRate> currentRequest = Single.fromCallable(() -> Optional.from(currencyDao.getExchangeRate(currentRequestDate)))
                    .flatMap(currencyWithExchangeRateOptional -> currencyWithExchangeRateOptional.isNull() ?
                            currencyApi.getPrivateApi(currentRequestDate)
                                    .map(this::convert)
                                    .doOnSuccess(this::insertIfNotEmpty):
                            Single.just(currencyWithExchangeRateOptional.get()));
            requests.add(currentRequest);
        }
        return Single.merge(requests).toObservable();
    }
    private void insertIfNotEmpty(CurrencyWithExchangeRate currencyWithExchangeRate){
        if(!currencyWithExchangeRate.getExchangeRateSaves().isEmpty())
            currencyDao.insert(currencyWithExchangeRate);
    }
    private CurrencyWithExchangeRate convert(PrivateApiResponse privateApiResponse){
        List<ExchangeRateSave> exchangeRateSaveList=new ArrayList<>();
        for (ExchangeRate exchangeRate:privateApiResponse.getExchangeRate())
            if(exchangeRate.getCurrency()!=null)
                exchangeRateSaveList.add(new ExchangeRateSave(exchangeRate));
        return new CurrencyWithExchangeRate(new CurrencySave(privateApiResponse),exchangeRateSaveList);

    }
}
