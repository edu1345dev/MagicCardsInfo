package com.android.josesantos.magiccardsinfo.presentation.main;

import com.android.josesantos.magiccardsinfo.presentation.base.BasePresenter;

import java.util.List;

/**
 * Created by josesantos on 24/12/17.
 */

public interface MainContracts {

    interface View {
        void onCardNamesResult(List<String> cardNames);
    }

    interface Presenter extends BasePresenter<MainContracts.View> {
        void searchCardName(String cardName);
    }
}
