package com.android.josesantos.magiccardsinfo.model.repository;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse;

import org.jetbrains.annotations.Nullable;

import io.reactivex.Single;

/**
 * Created by josesantos on 24/12/17.
 */

public interface MagicApiRepository {
    Single<MagicApiResponse> getMagicCardsByName(String cardName);

    void setLanguage(@Nullable String language);
}
