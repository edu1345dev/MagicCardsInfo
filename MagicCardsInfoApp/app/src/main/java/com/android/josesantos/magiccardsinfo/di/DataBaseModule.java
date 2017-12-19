package com.android.josesantos.magiccardsinfo.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.android.josesantos.magiccardsinfo.data.database.AppDatabase;
import com.android.josesantos.magiccardsinfo.data.database.dao.MagicCardInfoDao;
import com.android.josesantos.magiccardsinfo.data.ligamagic.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by josesantos on 18/12/17.
 */
@Module
public class DataBaseModule {
    private static final String DATABASE = "magic_database";

    @Provides
    @Named(DATABASE)
    String provideDatabaseName(){
        return Const.DATABASE_NAME;
    }

    @Provides
    @Singleton
    AppDatabase providesMagicCardsDatabase(Context context, @Named(DATABASE) String databaseName){
        return Room.databaseBuilder(context, AppDatabase.class, databaseName).build();
    }

    @Provides
    @Singleton
    MagicCardInfoDao providesMagicCardInfoDao(AppDatabase appDatabase){
        return appDatabase.magicCardInfoDao();
    }
}
