package com.android.josesantos.magiccardsinfo.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.josesantos.magiccardsinfo.data.ligamagic.MagicCardInfo;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by josesantos on 18/12/17.
 */

@Dao
public interface MagicCardInfoDao {

    @Query("SELECT * FROM card_ligamagic")
    Flowable<MagicCardInfo>  getAllLigamagicCards();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MagicCardInfo magicCardInfo);
}
