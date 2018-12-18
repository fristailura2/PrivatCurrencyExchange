package com.fastsoft.testcurrencyexchange.presentation.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<T extends BasePresenter> extends DaggerFragment implements BaseView{
    protected T presenter;
    private final static String RECTEATION_FLAG_KEY="recreation_key";
    @Override
    public void showMessage(String text) {
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG).show();
    }

    public T getPresenter() {
        return presenter;
    }
    public abstract void setPresenter(T presenter);

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RECTEATION_FLAG_KEY,true);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if((getArguments() != null && getArguments().containsKey(RECTEATION_FLAG_KEY)) &&getArguments().getBoolean(RECTEATION_FLAG_KEY))
            return;
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        presenter.bindView(this);
    }
}
