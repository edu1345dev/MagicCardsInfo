package com.android.josesantos.magiccardsinfo.presentation.main.di;

import com.android.josesantos.magiccardsinfo.presentation.main.FilterCardsContracts;
import com.android.josesantos.magiccardsinfo.presentation.main.MainContracts;

import dagger.Module;
import dagger.Provides;

/**
 * Created by josesantos on 26/12/17.
 */
@Module
public class FilterCardsPresenterModule {
    private FilterCardsContracts.View view;

    public FilterCardsPresenterModule(FilterCardsContracts.View view) {
        this.view = view;
    }

    @Provides
    public FilterCardsContracts.View providesMainView() {
        return view;
    }
}
