package com.android.josesantos.magiccardsinfo.di;

import com.android.josesantos.magiccardsinfo.presentation.main.MainActivity;

import dagger.Component;

/**
 * Created by josesantos on 18/12/17.
 */

@Component(modules = {AppModule.class, DataBaseModule.class, ApiServiceModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
