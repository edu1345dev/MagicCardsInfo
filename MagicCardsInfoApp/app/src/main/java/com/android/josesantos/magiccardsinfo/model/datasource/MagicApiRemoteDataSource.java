package com.android.josesantos.magiccardsinfo.model.datasource;

import com.android.josesantos.magiccardsinfo.data.api.LanguageConstants;
import com.android.josesantos.magiccardsinfo.data.api.magicApi.MagicApiService;
import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by josesantos on 24/12/17.
 */

public class MagicApiRemoteDataSource implements MagicApiDataSource {

    MagicApiService magicApiService;

    @Inject
    public MagicApiRemoteDataSource(MagicApiService magicApiService) {
        this.magicApiService = magicApiService;
    }

    @Override
    public Single<MagicApiResponse> getCardsFromApiByName(String cardName) {
        return magicApiService.getMagicCardsByName(cardName, LanguageConstants.EN_US);
    }
}
