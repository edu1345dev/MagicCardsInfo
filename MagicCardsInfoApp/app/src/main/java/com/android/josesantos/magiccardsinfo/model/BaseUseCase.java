package com.android.josesantos.magiccardsinfo.model;

import dagger.internal.Preconditions;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by josesantos on 24/12/17.
 */

public class BaseUseCase {
    private final CompositeDisposable disposables;

    public BaseUseCase() {
        this.disposables = new CompositeDisposable();
    }

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    public void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }
}
