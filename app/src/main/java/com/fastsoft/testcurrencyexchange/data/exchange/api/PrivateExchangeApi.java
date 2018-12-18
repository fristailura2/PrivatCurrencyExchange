package com.fastsoft.testcurrencyexchange.data.exchange.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PrivateExchangeApi {
    @GET("p24api/exchange_rates?json")
    Single<PrivateApiResponse> getPrivateApi(@Query("date") String date);
}
