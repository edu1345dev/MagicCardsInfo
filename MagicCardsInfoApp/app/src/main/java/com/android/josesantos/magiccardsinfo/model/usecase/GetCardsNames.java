package com.android.josesantos.magiccardsinfo.model.usecase;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse;
import com.android.josesantos.magiccardsinfo.model.BaseUseCase;
import com.android.josesantos.magiccardsinfo.model.repository.MagicApiRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by josesantos on 24/12/17.
 */

public class GetCardsNames extends BaseUseCase {

    MagicApiRepository magicApiRepository;

    @Inject
    public GetCardsNames(MagicApiRepository magicApiRepository) {
        this.magicApiRepository = magicApiRepository;
    }

    public void execute(String cardName, DisposableSingleObserver<MagicApiResponse> observer) {
        magicApiRepository.getMagicCardsByName(cardName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);

        addDisposable(observer);
    }
}
