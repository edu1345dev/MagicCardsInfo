package com.example.josesantos.transitionsstudy.data.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by josesantos on 03/12/17.
 */

public class ApiService {

    private static final String BASE_URL = "https://api.magicthegathering.io/v1/";
    private static final int TIMEOUT_SECONDS = 30;

    protected static <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClientBuilder = getHttpClient().newBuilder();

        httpClientBuilder.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        return getRetrofit(httpClientBuilder.build()).create(serviceClass);
    }

    public static Retrofit getRetrofit() {
        return getRetrofit(ApiService.getHttpClient());
    }

    private static Retrofit getRetrofit(OkHttpClient httpClient) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        return builder.client(httpClient).build();
    }

    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);

        return builder.build();
    }
}
