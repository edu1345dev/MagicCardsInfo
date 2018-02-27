package com.android.josesantos.magiccardsinfo.presentation.main;

import com.android.josesantos.magiccardsinfo.presentation.base.ActivityScope;
import com.android.josesantos.magiccardsinfo.util.SchedulerModule;

import dagger.Component;

/**
 * Created by josesantos on 26/12/17.
 */
@ActivityScope
@Component(modules = {MainPresenterModule.class, SchedulerModule.class})
public interface MainComponent {
}
