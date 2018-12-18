package com.fastsoft.testcurrencyexchange.data.exchange.db.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.fastsoft.testcurrencyexchange.data.exchange.api.ExchangeRate;

@Entity(foreignKeys = @ForeignKey(entity = CurrencySave.class,childColumns = {"currencySaveId"},parentColumns = {"id"},onDelete = ForeignKey.CASCADE),indices = {@Index(value = "currencySaveId")})
public class ExchangeRateSave extends ExchangeRate {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long currencySaveId;

    public ExchangeRateSave() {
    }

    public ExchangeRateSave(ExchangeRate other) {
        super(other);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCurrencySaveId() {
        return currencySaveId;
    }

    public void setCurrencySaveId(long currencySaveId) {
        this.currencySaveId = currencySaveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRateSave)) return false;

        ExchangeRateSave that = (ExchangeRateSave) o;

        if (getId() != that.getId()) return false;
        return getCurrencySaveId() == that.getCurrencySaveId();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (getCurrencySaveId() ^ (getCurrencySaveId() >>> 32));
        return result;
    }
}
