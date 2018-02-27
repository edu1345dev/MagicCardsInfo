package com.android.josesantos.magiccardsinfo.di;

import com.android.josesantos.magiccardsinfo.data.api.HeaderInterceptor;
import com.android.josesantos.magiccardsinfo.data.api.magicApi.MagicApiService;
import com.android.josesantos.magiccardsinfo.data.ligamagic.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by josesantos on 18/12/17.
 */

@Module
public class ApiServiceModule {
    private static final String BASE_URL = "base_url";

    @Provides
    @Named(BASE_URL)
    String provideBaseUrl(){
        return Const.MAGIC_API_BASE_URL;
    }

    @Provides
    @Singleton
    HeaderInterceptor provideHeaderInterceptor() {
        return new HeaderInterceptor();
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(HeaderInterceptor headerInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(headerInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Converter.Factory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    CallAdapter.Factory provideRxJavaAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@Named(BASE_URL) String baseUrl, Converter.Factory converterFactory,
                             CallAdapter.Factory callAdapterFactory, OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build();
    }

    /* Specific services */
    @Provides
    @Singleton
    MagicApiService provideMagicApiService(Retrofit retrofit) {
        return retrofit.create(MagicApiService.class);
    }
}