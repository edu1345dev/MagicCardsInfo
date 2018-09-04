package com.android.josesantos.magiccardsinfo.data.ligamagic;

import com.opencsv.bean.CsvBindByName;

import org.jetbrains.annotations.Nullable;

/**
 * Created by josesantos on 24/11/17.
 */

public class LojaInfo {

    public static final String STORE_LABEL = "STORE_LABEL";
    public static final String STORE_DATA = "STORE_DATA";

    @CsvBindByName
    private String storeName;
    @CsvBindByName
    private String edition;
    @CsvBindByName
    private String price;
    @CsvBindByName
    private String promoPrice;
    @CsvBindByName
    private String qtd;
    private String lojaUrl;
    @Nullable
    public String condition;
    private String cardName;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(String promoPrice) {
        this.promoPrice = promoPrice;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getLojaUrl() {
        return lojaUrl;
    }

    public void setLojaUrl(String lojaUrl) {
        this.lojaUrl = lojaUrl;
    }
}
