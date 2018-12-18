package com.fastsoft.testcurrencyexchange.presentation.main.privat;

import com.fastsoft.testcurrencyexchange.data.exchange.ExchengeRepository;
import com.fastsoft.testcurrencyexchange.di.annotaion.FragmentScope;
import com.fastsoft.testcurrencyexchange.presentation.base.bank.BankPresenter;

import javax.inject.Inject;

@FragmentScope
public class PrivatePresenter extends BankPresenter<PrivateView> {
    @Inject
    public PrivatePresenter(ExchengeRepository exchengeRepository) {
        super(exchengeRepository);
        setFilter(exchangeRateSave -> Double.doubleToLongBits(exchangeRateSave.getPurchaseRate())!=Double.doubleToLongBits(0d));
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().sendClickObservableUp();
    }

}
