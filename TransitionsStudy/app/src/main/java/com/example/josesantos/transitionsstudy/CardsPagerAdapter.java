package com.example.josesantos.transitionsstudy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josesantos on 25/11/17.
 */

public class CardsPagerAdapter extends FragmentStatePagerAdapter {

    private List<MagicCardInfo> listCards = new ArrayList<>();

    public CardsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setListCards(List<MagicCardInfo> listCards) {
        this.listCards = listCards;
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.newInstance(listCards.get(position));
    }

    @Override
    public int getCount() {
        return listCards.size();
    }
}
