package com.android.josesantos.magiccardsinfo.data.api.magicApi;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by josesantos on 03/12/17.
 */

public interface MagicApiService {

    @GET("cards")
    Single<MagicApiResponse> getMagicCardsByName(@Query("name") String name,
                                                 @Query("main_menu") String language);

}
