package com.fastsoft.testcurrencyexchange.presentation.main.nbu;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.fastsoft.testcurrencyexchange.R;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.presentation.base.bank.BankFragment;
import com.fastsoft.testcurrencyexchange.presentation.main.BankItemClickObserverReceiver;
import com.fastsoft.testcurrencyexchange.utils.CurrencyCodeUtils;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;

public class NBUFragment extends BankFragment<NBUPresenter> implements NBUView,BankItemClickObserverReceiver {
    NBUAdapter adapter=new NBUAdapter();
    public NBUFragment() {
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_nbu;
    }

    @Override
    public int getRecyclerId() {
        return R.id.fragment_nbu_recycler;
    }

    @Override
    public int getTitleId() {
        return R.string.nbu;
    }

    @Override
    public NBUFragment.NBUAdapter newAdapter() {
        return adapter;
    }

    @Override
    @Inject
    public void setPresenter(NBUPresenter presenter) {
        this.presenter=presenter;
    }
    @Override
    public void scrollTo(ExchangeRateSave exchangeRateSave){
        ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(adapter.getItemIndex(exchangeRateSave.getCurrency()),recyclerView.getMeasuredHeight()/2);
        adapter.selectAndHighlight(exchangeRateSave.getCurrency());
    }

    @Override
    public void onObservableReceive(Observable<ExchangeRateSave> observable) {
        presenter.onPrivateClickObservableReceived(observable);
    }

    public class NBUAdapter extends BankFragment.BankAdapter {

        @Override
        protected NBUViewHolder newHolder(View itemView) {
            return new NBUViewHolder(itemView);
        }

        @Override
        protected int getItemLayout() {
            return R.layout.layout_nbu_item;
        }

        public class NBUViewHolder extends BankAdapter.BankViewHolder {
            @BindView(R.id.layout_nbu_item_currency_textView)
            TextView currencyTextView;
            @BindView(R.id.layout_nbu_item_price_base_textView)
            TextView saleRateTextView;
            @BindView(R.id.layout_nbu_item_price_textView)
            TextView saleRatePefTextView;

            private static final String FLOAT_FORMAT="%.2f%s";
            public NBUViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void bindPrivate(ExchangeRateSave exchangeRate, int index) {
                Resources resources=getResources();
                if(index%2==0)
                    root.setBackground(new ColorDrawable(resources.getColor(R.color.colorPrimary)));
                else
                    root.setBackground(new ColorDrawable(resources.getColor(R.color.default_background)));

                double baseCurrencyVal=exchangeRate.getPurchaseRateNB();
                double otherCurrencyVal=1d;

                if(baseCurrencyVal==0)
                    baseCurrencyVal=1;
                while(baseCurrencyVal<1) {
                    baseCurrencyVal *= 10;
                    otherCurrencyVal *= 10;
                }

                saleRatePefTextView.setText(String.format(FLOAT_FORMAT,otherCurrencyVal,exchangeRate.getCurrency()));
                saleRateTextView.setText(String.format(FLOAT_FORMAT,baseCurrencyVal,exchangeRate.getBaseCurrency()));

                String currencyName = CurrencyCodeUtils.getNameByCode(getResources(), exchangeRate.getCurrency());
                currencyTextView.setText(currencyName==null?exchangeRate.getCurrency():currencyName);
            }

        }

    }
}
