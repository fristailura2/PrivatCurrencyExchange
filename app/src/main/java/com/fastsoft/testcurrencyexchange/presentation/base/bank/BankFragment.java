package com.fastsoft.testcurrencyexchange.presentation.base.bank;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fastsoft.testcurrencyexchange.R;
import com.fastsoft.testcurrencyexchange.data.exchange.db.models.ExchangeRateSave;
import com.fastsoft.testcurrencyexchange.presentation.base.BaseFragment;
import com.fastsoft.testcurrencyexchange.utils.ArraysUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BankFragment<T extends BankPresenter> extends BaseFragment<T> implements BankView{
    private final static String DATE_KEY="date_key";

    @BindView(R.id.layout_nbu_item_date_textView)
    TextView dateTextView;
    @BindView(R.id.layout_nbu_item_bank_name_textView)
    TextView bankNameTextView;

    protected BankAdapter adapter;
    protected RecyclerView recyclerView;

    @Inject
    @Override
    public void setPresenter(T presenter) {
        this.presenter=presenter;
    }
    @LayoutRes
    public abstract int getFragmentLayout();
    @IdRes
    public abstract int getRecyclerId();
    @StringRes
    public abstract int getTitleId();
    public abstract <T extends BankAdapter>T newAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bankNameTextView.setText(getTitleId());
        initRecycler(view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String date= dateTextView.getText().toString();
        outState.putString(DATE_KEY,date);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null&&savedInstanceState.containsKey(DATE_KEY))
            presenter.onOrientationChanged(savedInstanceState.getString(DATE_KEY));
    }
    public void initRecycler(@NonNull View view){
        recyclerView=view.findViewById(getRecyclerId());
        adapter=newAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setExchangeRateSaves(List<ExchangeRateSave> exchangeRateSaves){
        adapter.setExchangeRateSaves(exchangeRateSaves);
    }
    @OnClick(R.id.layout_nbu_item_date_picker_button_appCompatButton)
    public void onPickDateButtonClick(){
        presenter.onPickDateButtonClick();
    }

    @Override
    public void showDataPickerDialog() {
        Calendar calendar=Calendar.getInstance();
        new DatePickerDialog(getActivity(), (view, year, monthOfYear, dayOfMonth) -> presenter.onDataPicked(year, monthOfYear, dayOfMonth),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    @Override
    public void showDate(String date) {
        dateTextView.setText(date);
    }

    public abstract class BankAdapter<V extends BankAdapter.BankViewHolder> extends RecyclerView.Adapter<V> {
        protected List<ExchangeRateSave> exchangeRateSaves = new ArrayList<>();
        protected ExchangeRateSave selectedExchangeRateSave;

        public void selectAndHighlight(String currency) {
            ExchangeRateSave exchangeRateSave = ArraysUtils.find(exchangeRateSaves, val -> val.getCurrency().equals(currency));

            if (exchangeRateSave == null || exchangeRateSave.equals(selectedExchangeRateSave))
                return;

            int itemIndex = exchangeRateSaves.indexOf(selectedExchangeRateSave);
            this.selectedExchangeRateSave = exchangeRateSave;

            if (itemIndex != -1)
                notifyItemChanged(itemIndex);

            itemIndex = exchangeRateSaves.indexOf(selectedExchangeRateSave);
            notifyItemChanged(itemIndex);
        }

        public int getItemIndex(String currency) {
            ExchangeRateSave exchangeRateSave = ArraysUtils.find(exchangeRateSaves, val -> val.getCurrency().equals(currency));
            if (exchangeRateSave == null)
                return 0;
            return Math.min(exchangeRateSaves.indexOf(exchangeRateSave) + 1, exchangeRateSaves.size() - 1);
        }

        @NonNull
        @Override
        public V onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(getContext()).inflate(getItemLayout(), viewGroup, false);
            return newHolder(itemView);
        }
        protected abstract V newHolder(View itemView);
        @LayoutRes
        protected abstract int getItemLayout();

        public void setExchangeRateSaves(List<ExchangeRateSave> exchangeRateSaves) {
            this.exchangeRateSaves.clear();
            this.exchangeRateSaves.addAll(exchangeRateSaves);
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull V bankViewHolder, int i) {
            bankViewHolder.bind(exchangeRateSaves.get(i), i);
        }

        @Override
        public int getItemCount() {
            return exchangeRateSaves.size();
        }

        public abstract class BankViewHolder extends RecyclerView.ViewHolder {
            protected View root;

            protected ExchangeRateSave model;

            public BankViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                this.root = itemView;
            }

            public void bind(ExchangeRateSave exchangeRate, int index) {
                this.model = exchangeRate;

                bindPrivate(exchangeRate, index);

                if (model.equals(selectedExchangeRateSave))
                    root.setBackground(new ColorDrawable(getContext().getResources().getColor(R.color.colorPrimaryDark)));
            }

            public abstract void bindPrivate(ExchangeRateSave exchangeRate, int index);
        }
    }
}
