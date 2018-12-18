package com.fastsoft.testcurrencyexchange.presentation.main;

import com.fastsoft.testcurrencyexchange.di.annotaion.ActivityScope;
import com.fastsoft.testcurrencyexchange.presentation.base.BasePresenter;

import javax.inject.Inject;

@ActivityScope
public class MainPresenter extends BasePresenter<MainView> {
    @Inject
    public MainPresenter() {
    }

    public void onMenuGraphButtonClick() {
        getView().openGraphActivity();
    }
}
