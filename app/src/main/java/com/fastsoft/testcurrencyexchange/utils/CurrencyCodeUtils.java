package com.fastsoft.testcurrencyexchange.utils;

import android.content.res.Resources;

import com.fastsoft.testcurrencyexchange.R;

public class CurrencyCodeUtils {
    public static String getNameByCode(Resources resources,String code){
        String[] keyValArray=resources.getStringArray(R.array.key_val_currency_code);
        for (String keyVal:keyValArray) {
            String[] keyAndVal=keyVal.split("[,]");
            if(keyAndVal[0].equals(code))
                return keyAndVal[1];
        }
        return null;
    }
}
