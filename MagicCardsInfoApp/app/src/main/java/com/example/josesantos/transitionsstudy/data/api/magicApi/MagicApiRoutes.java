package com.example.josesantos.transitionsstudy.data.api.magicApi;

import com.example.josesantos.transitionsstudy.data.entity.MagicApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by josesantos on 03/12/17.
 */

public interface MagicApiRoutes {

    @GET("cards")
    Observable<MagicApiResponse> getMagicCardsByName(@Query("name") String name,
                                                     @Query("language") String language);

}