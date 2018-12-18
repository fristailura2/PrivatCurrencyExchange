
package com.fastsoft.testcurrencyexchange.data.exchange.api;

import java.io.Serializable;
import java.util.List;

public class PrivateApiResponse implements Serializable {

private String date;
private String bank;
private int baseCurrency;
private String baseCurrencyLit;
private List<ExchangeRate> exchangeRate = null;
private final static long serialVersionUID = 8158297174769096074L;

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getBank() {
return bank;
}

public void setBank(String bank) {
this.bank = bank;
}

public int getBaseCurrency() {
return baseCurrency;
}

public void setBaseCurrency(int baseCurrency) {
this.baseCurrency = baseCurrency;
}

public String getBaseCurrencyLit() {
return baseCurrencyLit;
}

public void setBaseCurrencyLit(String baseCurrencyLit) {
this.baseCurrencyLit = baseCurrencyLit;
}

public List<ExchangeRate> getExchangeRate() {
return exchangeRate;
}

public void setExchangeRate(List<ExchangeRate> exchangeRate) {
this.exchangeRate = exchangeRate;
}



}