package com.fastsoft.testcurrencyexchange.utils;

public class Optional<T> {
    private T val;

    public Optional(T val) {
        this.val = val;
    }

    public T get(){
        return val;
    }
    public void set(T val){
        this.val=val;
    }
    public boolean isNull(){
        return val==null;
    }
    public static <V>Optional<V> from(V val){
        return new Optional<>(val);
    }
}
