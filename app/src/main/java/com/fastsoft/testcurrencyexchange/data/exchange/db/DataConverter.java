package com.fastsoft.testcurrencyexchange.data.exchange.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;


public class DataConverter {
    @TypeConverter
    public Date convert(long time){
        return new Date(time);
    }
    @TypeConverter
    public long convert(Date date){
        return date.getTime();
    }
}
