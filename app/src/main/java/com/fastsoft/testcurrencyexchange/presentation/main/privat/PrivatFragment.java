package com.fastsoft.testcurrencyexchange.presentation.main.privat;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.fastsoft.testcurrencyexchange.R;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.presentation.base.bank.BankFragment;
import com.fastsoft.testcurrencyexchange.presentation.main.BankItemClickObserverReceiver;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PrivatFragment extends BankFragment<PrivatePresenter> implements PrivateView{

    private PublishSubject<ExchangeRateSave> onClickSubject =PublishSubject.create();

    public PrivatFragment() {
    }

    @Override
    public void sendClickObservableUp(){
        FragmentActivity upActivity = getActivity();
        if(upActivity instanceof BankItemClickObserverReceiver)
            ((BankItemClickObserverReceiver)upActivity).onObservableReceive(getClickObservable());
    }

    @Override
    public Observable<ExchangeRateSave> getClickObservable(){
        return onClickSubject;
    }


    @Override
    @Inject
    public void setPresenter(PrivatePresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_privat;
    }

    @Override
    public int getRecyclerId() {
        return R.id.fragment_privat_recycler;
    }

    @Override
    public int getTitleId() {
        return R.string.privat;
    }

    @Override
    public PrivateAdapter newAdapter() {
        return new PrivateAdapter();
    }

    public class PrivateAdapter extends BankFragment.BankAdapter{

        @Override
        protected PrivateViewHolder newHolder(View itemView) {
            return new PrivateViewHolder(itemView);
        }

        @Override
        protected int getItemLayout() {
            return R.layout.layout_private_item;
        }

        public class PrivateViewHolder extends BankFragment.BankAdapter.BankViewHolder {
            @BindView(R.id.textView9)
            TextView currencyTextView;
            @BindView(R.id.textView10)
            TextView saleRateTextView;
            @BindView(R.id.textView11)
            TextView saleRatePefTextView;
            View root;

            private static final String FLOAT_FORMAT="%.3f";
            public PrivateViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
                this.root=itemView;
            }

            @Override
            public void bindPrivate(ExchangeRateSave exchangeRate, int index) {
                saleRatePefTextView.setText(String.format(FLOAT_FORMAT,exchangeRate.getPurchaseRate()));
                saleRateTextView.setText(String.format(FLOAT_FORMAT,exchangeRate.getSaleRate()));
                currencyTextView.setText(exchangeRate.getCurrency());
                root.setBackground(new ColorDrawable(getResources().getColor(R.color.default_background)));
            }

            @OnClick(R.id.layout_private_item_root)
            public void onItemClick(){
                selectAndHighlight(model.getCurrency());
                onClickSubject.onNext(model);
            }
        }
    }
}
