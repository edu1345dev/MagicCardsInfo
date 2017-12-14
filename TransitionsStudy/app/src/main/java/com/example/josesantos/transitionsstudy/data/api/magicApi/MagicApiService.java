package com.example.josesantos.transitionsstudy.data.api.magicApi;

import com.example.josesantos.transitionsstudy.data.api.ApiService;
import com.example.josesantos.transitionsstudy.data.api.LanguageConstants;
import com.example.josesantos.transitionsstudy.data.entity.MagicApiResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by josesantos on 03/12/17.
 */

public class MagicApiService extends ApiService {

    @Inject
    public MagicApiService() {
        //used by dagger
    }

    public Observable<MagicApiResponse> getMagicCardsByName(String name){
        return getService().getMagicCardsByName(name, LanguageConstants.EN_US);
    }

    private MagicApiRoutes getService() {
        return createService(MagicApiRoutes.class);
    }
}
