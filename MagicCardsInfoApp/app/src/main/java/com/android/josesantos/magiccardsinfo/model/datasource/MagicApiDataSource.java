package com.android.josesantos.magiccardsinfo.model.datasource;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse;

import io.reactivex.Single;

/**
 * Created by josesantos on 24/12/17.
 */

public interface MagicApiDataSource {

    Single<MagicApiResponse> getCardsFromApiByName(String cardName);
}
