package com.android.josesantos.magiccardsinfo.presentation.main.di;

import com.android.josesantos.magiccardsinfo.di.MainRepositoryComponent;
import com.android.josesantos.magiccardsinfo.presentation.base.ActivityScope;
import com.android.josesantos.magiccardsinfo.presentation.main.FilterCardsActivity;
import com.android.josesantos.magiccardsinfo.presentation.main.MainActivity;
import com.android.josesantos.magiccardsinfo.util.SchedulerModule;

import dagger.Component;

/**
 * Created by josesantos on 26/12/17.
 */
@ActivityScope
@Component(modules = {FilterCardsPresenterModule.class, SchedulerModule.class}, dependencies = {MainRepositoryComponent.class})
public interface FilterCardsComponent {
    void inject(FilterCardsActivity activityKotlin);
}
