package com.android.josesantos.magiccardsinfo.di;

import com.android.josesantos.magiccardsinfo.model.repository.MagicApiRepositoryImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@Singleton
@Component(modules = {MainRepositoryModule.class, AppModule.class, ApiServiceModule.class,
        DataBaseModule.class})
public interface MainRepositoryComponent {
    MagicApiRepositoryImpl provideMagicApiRepository();
}
