package com.android.josesantos.magiccardsinfo.model.repository;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse;
import com.android.josesantos.magiccardsinfo.model.datasource.MagicApiDataSource;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by josesantos on 24/12/17.
 */

public class MagicApiRepositoryImpl implements MagicApiRepository {

    MagicApiDataSource magicApiDataSource;

    @Inject
    public MagicApiRepositoryImpl(MagicApiDataSource magicApiDataSource) {
        this.magicApiDataSource = magicApiDataSource;
    }

    @Override
    public Single<MagicApiResponse> getMagicCardsByName(String cardName) {
        return magicApiDataSource.getCardsFromApiByName(cardName);
    }
}
