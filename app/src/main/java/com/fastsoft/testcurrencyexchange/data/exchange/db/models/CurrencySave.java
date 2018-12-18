package com.fastsoft.testcurrencyexchange.data.exchange.db.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.fastsoft.testcurrencyexchange.data.exchange.api.PrivateApiResponse;

import java.util.Currency;

@Entity
public class CurrencySave {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String date;
    private String baseCurrencyLit;

    public CurrencySave(PrivateApiResponse privateApiResponse){
        this.date=privateApiResponse.getDate();
        this.baseCurrencyLit=privateApiResponse.getBaseCurrencyLit();
    }

    public CurrencySave() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public void setBaseCurrencyLit(String baseCurrencyLit) {
        this.baseCurrencyLit = baseCurrencyLit;
    }
}
