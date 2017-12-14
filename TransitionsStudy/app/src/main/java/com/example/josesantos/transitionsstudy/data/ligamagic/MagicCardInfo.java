package com.example.josesantos.transitionsstudy.data.ligamagic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by josesantos on 25/11/17.
 */

public class MagicCardInfo implements Serializable {

    private String raridade;
    private String nomeEdicaoOficial;
    private String menorPreco;
    private String medioPreco;
    private String maiorPreco;
    private String imagem;

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
