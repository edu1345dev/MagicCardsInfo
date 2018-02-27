package com.android.josesantos.magiccardsinfo.di

import com.android.josesantos.magiccardsinfo.di.qualifiers.Remote
import com.android.josesantos.magiccardsinfo.model.datasource.MagicApiDataSource
import com.android.josesantos.magiccardsinfo.model.datasource.MagicApiRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by josesantos on 26/02/18.
 */
@Module
class MainRepositoryModule {

    @Provides
    @Singleton
    @Remote
    fun providesMagiApiDataSource(dataSource: MagicApiRemoteDataSource): MagicApiDataSource {
        return dataSource
    }
}