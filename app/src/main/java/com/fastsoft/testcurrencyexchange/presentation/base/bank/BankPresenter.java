package com.fastsoft.testcurrencyexchange.presentation.base.bank;

import com.fastsoft.testcurrencyexchange.data.exchange.ExchengeRepository;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.CurrencyWithExchangeRate;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.presentation.base.BasePresenter;
import com.fastsoft.testcurrencyexchange.utils.DateUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class BankPresenter<T extends BankView> extends BasePresenter<T> {
    protected ExchengeRepository exchengeRepository;
    protected CompositeDisposable compositeDisposable=new CompositeDisposable();
    protected Disposable dataDisposable;
    protected Predicate<ExchangeRateSave> filter=(exchangeRateSave)->true;

    public BankPresenter(ExchengeRepository exchengeRepository) {
        this.exchengeRepository = exchengeRepository;
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
    protected void setFilter(Predicate<ExchangeRateSave> filter){
        this.filter=filter;
    }
    public void onDataPicked(int year, int monthOfYear, int dayOfMonth) {
        disposeCurrencyWithExchangeRate();

        String formatedDate=DateUtils.getStringDate(year,monthOfYear,dayOfMonth);

        getView().showDate(formatedDate);
        compositeDisposable.add(exchengeRepository.getCurrencyWithExchangeRate(formatedDate)
                .subscribeOn(Schedulers.io())
                .map(CurrencyWithExchangeRate::getExchangeRateSaves)
                .flatMapObservable(exchangeRateSaves -> Observable.fromArray(exchangeRateSaves.toArray(new ExchangeRateSave[0])))
                .filter(filter)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(exchangeRateSaves -> {
                    if(exchangeRateSaves.isEmpty())
                        getView().showMessage("No data for "+formatedDate);
                })
                .subscribe(
                        exchangeRateSaves -> getView().setExchangeRateSaves(exchangeRateSaves),
                        throwable -> getView().showMessage("Some problem during getting data from api.privatbank.ua")));
    }
    private void disposeCurrencyWithExchangeRate(){
        if(dataDisposable !=null)
            dataDisposable.dispose();
        dataDisposable=null;
    }
    public void onPickDateButtonClick() {
        getView().showDataPickerDialog();
    }

    public void onOrientationChanged(String date) {
        try {
            if(!"".equals(date))
                onDataPicked(DateUtils.getYear(date),DateUtils.getMonth(date),DateUtils.getDayOfMonth(date));
        }catch (Exception e){}
    }
}
