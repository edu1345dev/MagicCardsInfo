package com.android.josesantos.magiccardsinfo.presentation.main;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiCard;
import com.android.josesantos.magiccardsinfo.presentation.MagicCardsInfoContract;
import com.android.josesantos.magiccardsinfo.presentation.base.BasePresenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by josesantos on 24/12/17.
 */

public interface MainContracts {

    interface View {
        void onCardNamesResult(List<MagicApiCard> cardNames);

        void hideProgress();

        void showCards(@Nullable List<? extends MagicApiCard> cardsList);
    }

    interface Presenter extends BasePresenter<MainContracts.View> {
        void searchCardName(String cardName);
        void setEnglishLanguage();
        void setPortugueseLanguage();
        void getSelectedCards(@NotNull String cardName);
    }
}
