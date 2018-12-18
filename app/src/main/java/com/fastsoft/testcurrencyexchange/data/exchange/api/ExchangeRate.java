package com.fastsoft.testcurrencyexchange.data.exchange.api;

import java.io.Serializable;

public class ExchangeRate implements Serializable {

private String baseCurrency;
private String currency;
private double saleRateNB;
private double purchaseRateNB;
private double saleRate;
private double purchaseRate;
private final static long serialVersionUID = -7604114760294605421L;

    public ExchangeRate(ExchangeRate other) {
        this.baseCurrency = other.baseCurrency;
        this.currency = other.currency;
        this.saleRateNB = other.saleRateNB;
        this.purchaseRateNB = other.purchaseRateNB;
        this.saleRate = other.saleRate;
        this.purchaseRate = other.purchaseRate;
    }

    public ExchangeRate() {
    }

    public String getBaseCurrency() {
return baseCurrency;
}

public void setBaseCurrency(String baseCurrency) {
this.baseCurrency = baseCurrency;
}

public String getCurrency() {
return currency;
}

public void setCurrency(String currency) {
this.currency = currency;
}

public double getSaleRateNB() {
return saleRateNB;
}

public void setSaleRateNB(double saleRateNB) {
this.saleRateNB = saleRateNB;
}

public double getPurchaseRateNB() {
return purchaseRateNB;
}

public void setPurchaseRateNB(double purchaseRateNB) {
this.purchaseRateNB = purchaseRateNB;
}

public double getSaleRate() {
return saleRate;
}

public void setSaleRate(double saleRate) {
this.saleRate = saleRate;
}

public double getPurchaseRate() {
return purchaseRate;
}

public void setPurchaseRate(double purchaseRate) {
this.purchaseRate = purchaseRate;
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRate)) return false;

        ExchangeRate that = (ExchangeRate) o;

        if (Double.compare(that.getSaleRateNB(), getSaleRateNB()) != 0) return false;
        if (Double.compare(that.getPurchaseRateNB(), getPurchaseRateNB()) != 0) return false;
        if (Double.compare(that.getSaleRate(), getSaleRate()) != 0) return false;
        if (Double.compare(that.getPurchaseRate(), getPurchaseRate()) != 0) return false;
        if (getBaseCurrency() != null ? !getBaseCurrency().equals(that.getBaseCurrency()) : that.getBaseCurrency() != null)
            return false;
        return getCurrency() != null ? getCurrency().equals(that.getCurrency()) : that.getCurrency() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getBaseCurrency() != null ? getBaseCurrency().hashCode() : 0;
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        temp = Double.doubleToLongBits(getSaleRateNB());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPurchaseRateNB());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getSaleRate());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPurchaseRate());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}