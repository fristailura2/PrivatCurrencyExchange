package com.fastsoft.testcurrencyexchange.presentation.base;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fastsoft.testcurrencyexchange.R;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<T extends BasePresenter> extends DaggerAppCompatActivity implements BaseView {
    protected T presenter;
    @Override
    public void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }
    public T getPresenter() {
        return presenter;
    }

    @LayoutRes
    public abstract int getLayout();

    public abstract void setPresenter(T presenter);

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
    private void initActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        presenter.bindView(this);
        initActionBar();
    }



}
