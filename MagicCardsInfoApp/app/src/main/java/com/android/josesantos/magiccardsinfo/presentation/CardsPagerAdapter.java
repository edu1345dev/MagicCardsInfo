package com.android.josesantos.magiccardsinfo.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.josesantos.magiccardsinfo.data.entity.MagicApiCard;
import com.android.josesantos.magiccardsinfo.data.ligamagic.MagicCardInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josesantos on 25/11/17.
 */

public class CardsPagerAdapter extends FragmentStatePagerAdapter {

    private List<MagicCardInfo> listCards = new ArrayList<>();
    private List<MagicApiCard> listApiCards = new ArrayList<>();

    public CardsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setListCards(List<MagicCardInfo> listCards) {
        this.listCards = listCards;
    }

    public void seApiListCards(List<MagicApiCard> listCards) {
        this.listApiCards = listCards;
    }

    @Override
    public Fragment getItem(int position) {
        if(listCards.isEmpty()){
            return ApiCardFragment.newInstance(listApiCards.get(position));
        }else {
            return CardFragment.newInstance(listCards.get(position));
        }
    }

    @Override
    public int getCount() {
        if(listCards.isEmpty()){
            return listApiCards.size();
        }else {
            return listCards.size();
        }
    }
}
