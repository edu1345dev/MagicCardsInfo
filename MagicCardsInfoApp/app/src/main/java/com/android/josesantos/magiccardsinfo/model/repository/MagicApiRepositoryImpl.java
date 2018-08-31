package com.android.josesantos.magiccardsinfo.model.repository;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse;
import com.android.josesantos.magiccardsinfo.di.qualifiers.Remote;
import com.android.josesantos.magiccardsinfo.model.datasource.MagicApiDataSource;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by josesantos on 24/12/17.
 */

public class MagicApiRepositoryImpl implements MagicApiRepository {

    private MagicApiDataSource magicApiDataSource;

    @Inject
    public MagicApiRepositoryImpl(@Remote MagicApiDataSource magicApiDataSource) {
        this.magicApiDataSource = magicApiDataSource;
    }

    @Override
    public Single<MagicApiResponse> getMagicCardsByName(String cardName) {
        return magicApiDataSource.getCardsFromApiByName(cardName);
    }

    @Override
    public void setLanguage(@Nullable String language) {
        magicApiDataSource.setLanguage(language);
    }
}
