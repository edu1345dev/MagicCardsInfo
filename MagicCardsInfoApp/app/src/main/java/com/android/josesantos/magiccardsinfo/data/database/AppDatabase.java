package com.android.josesantos.magiccardsinfo.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.RoomDatabase;

import com.android.josesantos.magiccardsinfo.data.database.dao.MagicCardInfoDao;
import com.android.josesantos.magiccardsinfo.data.ligamagic.MagicCardInfo;

/**
 * Created by josesantos on 18/12/17.
 */

@Database(entities = MagicCardInfo.class, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract MagicCardInfoDao magicCardInfoDao();
}
