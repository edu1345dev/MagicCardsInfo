package com.android.josesantos.magiccardsinfo.data.ligamagic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by josesantos on 25/11/17.
 */

@Entity(tableName = "card_ligamagic")
public class MagicCardInfo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String raridade;

    @ColumnInfo(name = "mode_edicao_oficial")
    private String nomeEdicaoOficial;
    @ColumnInfo(name = "menor_preco")
    private String menorPreco;
    @ColumnInfo(name = "medio_preco")
    private String medioPreco;
    @ColumnInfo(name = "maior_preco")
    private String maiorPreco;
    private String imagem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRaridade() {
        return raridade;
    }

    public void setRaridade(String raridade) {
        this.raridade = raridade;
    }

    public String getNomeEdicaoOficial() {
        return nomeEdicaoOficial;
    }

    public void setNomeEdicaoOficial(String nomeEdicaoOficial) {
        this.nomeEdicaoOficial = nomeEdicaoOficial;
    }

    public String getMenorPreco() {
        return menorPreco;
    }

    public void setMenorPreco(String menorPreco) {
        this.menorPreco = menorPreco;
    }

    public String getMedioPreco() {
        return medioPreco;
    }

    public void setMedioPreco(String medioPreco) {
        this.medioPreco = medioPreco;
    }

    public String getMaiorPreco() {
        return maiorPreco;
    }

    public void setMaiorPreco(String maiorPreco) {
        this.maiorPreco = maiorPreco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
