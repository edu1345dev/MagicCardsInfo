package com.android.josesantos.magiccardsinfo.util;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.android.josesantos.magiccardsinfo.util.SchedulerType.COMPUTATION;
import static com.android.josesantos.magiccardsinfo.util.SchedulerType.IO;
import static com.android.josesantos.magiccardsinfo.util.SchedulerType.UI;

/**
 * Provides common Schedulers used by RxJava
 *
 * @author QuangNguyen (quangctkm9207).
 */
@Module
public class SchedulerModule {

    @Provides
    @RunOn(IO)
    Scheduler provideIo() {
        return Schedulers.io();
    }

    @Provides
    @RunOn(COMPUTATION)
    Scheduler provideComputation() {
        return Schedulers.computation();
    }

    @Provides
    @RunOn(UI)
    Scheduler provideUi() {
        return AndroidSchedulers.mainThread();
    }
}
