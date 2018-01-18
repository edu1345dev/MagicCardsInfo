package com.android.josesantos.magiccardsinfo.presentation.main;

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
