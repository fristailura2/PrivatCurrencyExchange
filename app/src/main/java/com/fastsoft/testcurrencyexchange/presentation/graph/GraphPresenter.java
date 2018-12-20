package com.fastsoft.testcurrencyexchange.presentation.graph;

import com.fastsoft.testcurrencyexchange.data.exchange.ExchengeRepository;
import com.fastsoft.testcurrencyexchange.di.annotaion.ActivityScope;
import com.fastsoft.testcurrencyexchange.presentation.base.BasePresenter;
import com.fastsoft.testcurrencyexchange.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class GraphPresenter extends BasePresenter<GraphView> {
    private Date dateFrom;
    private Date dateTo;
    private ExchengeRepository exchengeRepository;
    private Disposable dateObservableDisposable;

    @Inject
    public GraphPresenter(ExchengeRepository exchengeRepository) {
        this.exchengeRepository=exchengeRepository;
    }

    public void onPickDateFromButtonClick() {
        getView().showDataPickerDialogFrom();
    }

    public void onPickDateToButtonClick() {
        getView().showDataPickerDialogTo();
    }

    public void onDateFromPicked(int year, int monthOfYear, int dayOfMonth) {
        dateFrom=DateUtils.getDate(year,monthOfYear,dayOfMonth);
        getView().setFromDateText(DateUtils.getStringDate(dateFrom));
        loadDateIfNotNull();
    }

    public void onDateToPicked(int year, int monthOfYear, int dayOfMonth) {
        dateTo=DateUtils.getDate(year,monthOfYear,dayOfMonth);
        getView().setToDateText(DateUtils.getStringDate(dateTo));
        loadDateIfNotNull();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(dateObservableDisposable!=null)
            dateObservableDisposable.dispose();
        dateObservableDisposable=null;
    }

    private void loadDateIfNotNull(){
        if(dateFrom!=null&&dateTo!=null){
            if(dateFrom.getTime()>dateTo.getTime()) {
                getView().showMessage("Incorrect date range from cant be after to");
                return;
            }
            if(new Date().before(dateTo)) {
                getView().showMessage("Date should be before now");
                return;
            }
            List<String> dates = DateUtils.getDaysForRangeAsString(dateFrom, dateTo);
            getView().showProgressDialog(dates.size());
            dateObservableDisposable =exchengeRepository.getCurrencyWithExchangeRate(dates)
                    .subscribeOn(Schedulers.io())
                    .filter(currencyWithExchangeRate -> !currencyWithExchangeRate.getExchangeRateSaves().isEmpty())
                    .map(currencyWithExchangeRate -> new GraphPoint(DateUtils.getDate(currencyWithExchangeRate.getCurrencySave().getDate()),
                            currencyWithExchangeRate.findExchangeRateSaveByCurrency("USD").getPurchaseRateNB()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(graphPoint -> getView().incProgressDialog())
                    .toList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(graphPoints -> getView().hideProgressDialog())
                    .subscribe(graphPoint -> getView().addGraphPoint(graphPoint));
        }
    }


    public void progressDialogCanceled() {
        dateObservableDisposable.dispose();
        dateObservableDisposable =null;
    }

    public void onOrientationChanged(boolean loading, String fromDate, String toDate, List<GraphPoint> graphData) {
        try {
            dateFrom=DateUtils.getDate(fromDate);
            dateTo=DateUtils.getDate(toDate);
            getView().setFromDateText(fromDate);
            getView().setToDateText(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(loading)
            loadDateIfNotNull();
        if(!graphData.isEmpty())
            getView().addGraphPoint(graphData);
    }
}
