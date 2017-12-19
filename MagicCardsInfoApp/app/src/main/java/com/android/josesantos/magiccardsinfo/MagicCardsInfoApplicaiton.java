package com.android.josesantos.magiccardsinfo;

import android.app.Application;

/**
 * Created by josesantos on 18/12/17.
 */

public class MagicCardsInfoApplicaiton extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDependencies();
    }

    private void initializeDependencies() {

    }
}
