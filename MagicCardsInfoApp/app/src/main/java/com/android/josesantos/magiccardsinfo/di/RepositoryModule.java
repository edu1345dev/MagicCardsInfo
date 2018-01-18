package com.android.josesantos.magiccardsinfo.di;

import com.android.josesantos.magiccardsinfo.di.qualifiers.Remote;
import com.android.josesantos.magiccardsinfo.model.datasource.MagicApiDataSource;
import com.android.josesantos.magiccardsinfo.model.datasource.MagicApiRemoteDataSource;
import com.android.josesantos.magiccardsinfo.model.repository.MagicApiRepository;
import com.android.josesantos.magiccardsinfo.model.repository.MagicApiRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by josesantos on 24/12/17.
 */
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    @Remote
    MagicApiDataSource providesMagiApiDataSource(MagicApiRemoteDataSource dataSource) {
        return dataSource;
    }

    @Provides
    @Singleton
    MagicApiRepository providesMagicApiRepo(MagicApiRepositoryImpl repository) {
        return repository;
    }
}
