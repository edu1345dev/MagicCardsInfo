package com.android.josesantos.magiccardsinfo.data.ligamagic;

import com.opencsv.bean.CsvBindByName;

/**
 * Created by josesantos on 24/11/17.
 */

public class LojaInfo {
    @CsvBindByName
    private String nome;
    @CsvBindByName
    private String edition;
    @CsvBindByName
    private String price;
    @CsvBindByName
    private String promoPrice;
    @CsvBindByName
    private String qtd;
    private String lojaUrl;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
