package com.fastsoft.testcurrencyexchange.presentation.base;

public class BasePresenter<T extends BaseView> {
    private T view;
    protected T getView(){
        return view;
    }
    public void bindView(T view){
        this.view=view;
    }
    public void onStart(){

    }
    public void onStop(){

    }
}
