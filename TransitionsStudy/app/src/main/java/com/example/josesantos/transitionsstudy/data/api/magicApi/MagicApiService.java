package com.example.josesantos.transitionsstudy.data.api.magicApi;

import com.example.josesantos.transitionsstudy.data.api.ApiService;
import com.example.josesantos.transitionsstudy.data.entity.MagicApiResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by josesantos on 03/12/17.
 */

public class MagicApiService extends ApiService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String SORT_BY = "primary_release_date.asc";

    @Inject
    public MagicApiService() {
        //used by dagger
    }

    public Observable<MagicApiResponse> getMagicCardsByName(String name){
        return getService().getMagicCardsByName(name);
    }

    private MagicApiRoutes getService() {
        return createService(MagicApiRoutes.class);
    }
}
