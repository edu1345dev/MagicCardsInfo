package com.android.josesantos.magiccardsinfo;

import android.app.Application;

import com.android.josesantos.magiccardsinfo.di.AppComponent;
import com.android.josesantos.magiccardsinfo.di.AppModule;
import com.android.josesantos.magiccardsinfo.di.DaggerAppComponent;
import com.android.josesantos.magiccardsinfo.di.DataBaseModule;
import com.android.josesantos.magiccardsinfo.di.MagicApiServiceModule;

/**
 * Created by josesantos on 18/12/17.
 */

public class MagicCardsInfoApplicaiton extends Application {

    private AppComponent appComponent = createComponent();

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataBaseModule(new DataBaseModule())
                .magicApiServiceModule(new MagicApiServiceModule())
                .build();
    }
}
