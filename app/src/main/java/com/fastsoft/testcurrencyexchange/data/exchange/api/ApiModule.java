package com.fastsoft.testcurrencyexchange.data.exchange.api;

import com.fastsoft.testcurrencyexchange.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    private static final String PRIVATE_API_BASE_URL=BuildConfig.PRIVATE_API_BASE_URL;
    private static final int TIMEOUT=35000;
    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(){
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .callTimeout(TIMEOUT,TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT,TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT,TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        return okHttpClient;
    }
    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PRIVATE_API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
    @Singleton
    @Provides
    public PrivateExchangeApi providePrivateCurrencyApi(Retrofit retrofit){
       return retrofit.create(PrivateExchangeApi.class);
    }
}
