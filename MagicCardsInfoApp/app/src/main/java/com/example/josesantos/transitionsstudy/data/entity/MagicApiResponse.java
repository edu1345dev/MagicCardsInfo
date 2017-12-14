package com.example.josesantos.transitionsstudy.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by josesantos on 13/12/17.
 */

public class MagicApiResponse {

    @SerializedName("cards")
    private List<MagicApiCard> cards;

    public List<MagicApiCard> getCards() {
        return cards;
    }

    public void setCards(List<MagicApiCard> cards) {
        this.cards = cards;
    }
}
