package com.android.josesantos.magiccardsinfo.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by josesantos on 18/12/17.
 */

@Singleton
@Component(modules = {AppModule.class, DataBaseModule.class, MagicApiServiceModule.class})
public interface MagicCardsInfoRepositoryComponent {

}
