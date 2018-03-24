package com.android.josesantos.magiccardsinfo.presentation.main.di;

import com.android.josesantos.magiccardsinfo.presentation.main.MainContracts;

import dagger.Module;
import dagger.Provides;

/**
 * Created by josesantos on 26/12/17.
 */
@Module
public class MainPresenterModule {
    private MainContracts.View view;

    public MainPresenterModule(MainContracts.View view) {
        this.view = view;
    }

    @Provides
    public MainContracts.View providesMainView() {
        return view;
    }
}
