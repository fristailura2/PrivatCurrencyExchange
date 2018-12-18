package com.fastsoft.testcurrencyexchange.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.fastsoft.testcurrencyexchange.R;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.presentation.base.BaseActivity;
import com.fastsoft.testcurrencyexchange.presentation.graph.GraphActivity;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView,BankItemClickObserverReceiver {
    @BindView(R.id.activity_main_root_linearLayout)
    LinearLayout linearLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    @LayoutRes
    public int getLayout(){
        return R.layout.activity_main;
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        linearLayout.setOrientation(2-getResources().getConfiguration().orientation);
    }
    @Override
    public void openGraphActivity(){
        Intent intent=new Intent();
        intent.setClass(getApplicationContext(),GraphActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_action_graph)
            presenter.onMenuGraphButtonClick();

        return super.onOptionsItemSelected(item);
    }

    @Override
    @Inject
    public void setPresenter(MainPresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void onObservableReceive(Observable<ExchangeRateSave> observable) {
        ((BankItemClickObserverReceiver)getSupportFragmentManager()
                .findFragmentById(R.id.activity_main_fragment_nbu))
                .onObservableReceive(observable);
    }
}
