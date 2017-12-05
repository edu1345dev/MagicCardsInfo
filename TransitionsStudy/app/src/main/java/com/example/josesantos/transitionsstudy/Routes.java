package com.example.josesantos.transitionsstudy;

import com.example.josesantos.transitionsstudy.data.MagicCardsData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by josesantos on 03/12/17.
 */

public interface Routes {

    @GET("card")
    Call<MagicCardsData> getUpcommingMoviesList(@Query("page") String page,
                                                @Query("language") String language);
}
