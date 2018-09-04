package com.android.josesantos.magiccardsinfo.presentation.main;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiCard;
import com.android.josesantos.magiccardsinfo.presentation.base.BasePresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by josesantos on 24/12/17.
 */

public interface FilterCardsContracts {

    interface View {
        void onCardNamesResult(List<MagicApiCard> cardNames);

        void hideProgress();
    }

    interface Presenter extends BasePresenter<FilterCardsContracts.View> {
        void searchCardName(String cardName);
        void setEnglishLanguage();
        void setPortugueseLanguage();
    }
}
