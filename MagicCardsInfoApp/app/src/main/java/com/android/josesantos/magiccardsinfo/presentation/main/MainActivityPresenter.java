package com.android.josesantos.magiccardsinfo.presentation.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.android.josesantos.magiccardsinfo.model.repository.MagicApiRepository;
import com.android.josesantos.magiccardsinfo.util.RunOn;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

import static com.android.josesantos.magiccardsinfo.util.SchedulerType.IO;
import static com.android.josesantos.magiccardsinfo.util.SchedulerType.UI;

/**
 * Created by josesantos on 26/12/17.
 */

public class MainActivityPresenter implements MainContracts.Presenter, LifecycleObserver {

    private MagicApiRepository magicApiRepository;
    private MainContracts.View view;
    private Scheduler io;
    private Scheduler ui;
    private CompositeDisposable disposable;

    @Inject
    public MainActivityPresenter(MagicApiRepository magicApiRepository,
                                 MainContracts.View view,
                                 @RunOn(IO) Scheduler io,
                                 @RunOn(UI) Scheduler ui,
                                 CompositeDisposable disposable) {
        this.magicApiRepository = magicApiRepository;
        this.view = view;
        this.io = io;
        this.ui = ui;
        this.disposable = disposable;

        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }

        disposable = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onDetach() {

    }

    @Override
    public void searchCardName(String cardName) {

    }
}
