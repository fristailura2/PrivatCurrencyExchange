package com.fastsoft.testcurrencyexchange.utils;

import android.util.Pair;

import java.util.Comparator;
import java.util.List;

public class ArraysUtils {
    public static <T>T findMin(List<T> array, Comparator<T> comparator){
        T min=array.get(0);
        for (T val:array)
            if(comparator.compare(min,val)==1)
                min=val;
        return min;
    }
    public static <T>T findMax(List<T> array, Comparator<T> comparator){
        T max=array.get(0);
        for (T val:array)
            if(comparator.compare(val,max)==1)
                max=val;
        return max;
    }
    public static <T>Pair<T,T> findMinMax(List<T> array, Comparator<T> comparator){
        T min=array.get(0);
        T max=array.get(0);
        for (T val:array) {
            if (comparator.compare(min, val) == 1)
                min = val;
            if (comparator.compare(val, max) == 1)
                max=val;
        }
        return new Pair<>(min,max);
    }
    public static <T>T find(List<T> array, Predicate<T> predicate){
        for (T t : array) {
            if(predicate.check(t))
                return t;
        }
        return null;
    }

    public interface Predicate<T>{
        boolean check(T val);
    }
    public interface Comparator<T>{
        int compare(T val1,T val2);
    }

}
