package com.android.josesantos.magiccardsinfo;

import android.app.Application;

import com.android.josesantos.magiccardsinfo.di.DaggerMainRepositoryComponent;
import com.android.josesantos.magiccardsinfo.di.MainRepositoryComponent;

/**
 * Created by josesantos on 18/12/17.
 */

public class MagicApplication extends Application {

    private static MagicApplication instance;
    private MainRepositoryComponent mainRepositoryComponent = createComponent();

    public static MagicApplication getInstance() {
        if (instance == null) {
            instance = new MagicApplication();
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private MainRepositoryComponent createComponent() {
        return DaggerMainRepositoryComponent.builder()
                .build();
    }

    public MainRepositoryComponent getMainRepositoryComponent() {
        return mainRepositoryComponent;
    }
}
