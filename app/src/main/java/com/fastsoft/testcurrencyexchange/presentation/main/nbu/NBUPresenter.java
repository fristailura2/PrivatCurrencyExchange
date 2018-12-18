package com.fastsoft.testcurrencyexchange.presentation.main.nbu;

import com.fastsoft.testcurrencyexchange.data.exchange.ExchengeRepository;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.di.annotaion.FragmentScope;
import com.fastsoft.testcurrencyexchange.presentation.base.BasePresenter;
import com.fastsoft.testcurrencyexchange.presentation.base.bank.BankPresenter;
import com.fastsoft.testcurrencyexchange.utils.DateUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
@FragmentScope
public class NBUPresenter extends BankPresenter<NBUView> {
    private Disposable privateItemClickDisposable;
    @Inject
    public NBUPresenter(ExchengeRepository exchengeRepository) {
        super(exchengeRepository);
    }

    public void onPrivateClickObservableReceived (Observable<ExchangeRateSave> observable) {
        if(privateItemClickDisposable!=null){
            privateItemClickDisposable.dispose();
            privateItemClickDisposable=null;
        }
        privateItemClickDisposable=observable.subscribe(exchangeRateSave -> getView().scrollTo(exchangeRateSave));
        compositeDisposable.add(privateItemClickDisposable);
    }

}
