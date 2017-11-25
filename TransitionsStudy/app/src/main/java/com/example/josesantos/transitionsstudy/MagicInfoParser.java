package com.example.josesantos.transitionsstudy;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josesantos on 24/11/17.
 */

public class MagicInfoParser {

    private static final String TAG = "MagicInfoParser";
    private List<String> listRaridade;
    private List<String> listNomeOficial;
    private List<String> listMenorPreco;
    private List<String> listMedioPreco;
    private List<String> listMaiorPreco;
    private List<String> listImagem;


    public void parse(String obj){
        String[] linhas = obj.split("\n");

        for (int i = 0; i < linhas.length; i++) {
            Log.d(TAG, "linha: "+i+", conteudo: "+linhas[i]);

            String[] colunas = linhas[i].split("=");

            for (int j = 0; j < colunas.length; j++) {
                Log.d(TAG, "coluna: "+j+", conteudo: "+colunas[j].replace(";","").replace("\"",""));

                if (colunas[j].contains("VET") && colunas[j].contains("[") && colunas[j].contains("]")){

                    String arrayName = colunas[j].substring(0, colunas[j].indexOf("["));

                    if (arrayName.equals(MagicInfoFields.RARIDADE)){
                        if (listRaridade == null){
                            listRaridade = new ArrayList<>();
                        }
                    }

                    if (arrayName.equals(MagicInfoFields.NOME_OFICIAL)){
                        if (listNomeOficial == null){
                            listNomeOficial = new ArrayList<>();
                        }
                    }

                    if (arrayName.equals(MagicInfoFields.MENOR_PRECO)){
                        if (listMenorPreco == null){
                            listMenorPreco = new ArrayList<>();
                        }
                    }

                    if (arrayName.equals(MagicInfoFields.MEDIO_PRECO)){
                        if (listMedioPreco == null){
                            listMedioPreco = new ArrayList<>();
                        }
                    }

                    if (arrayName.equals(MagicInfoFields.MAIOR_PRECO)){
                        if (listMaiorPreco == null){
                            listMaiorPreco = new ArrayList<>();
                        }
                    }

                    if (arrayName.equals(MagicInfoFields.IMAGEM)){
                        if (listImagem == null){
                            listImagem = new ArrayList<>();
                        }
                    }
                }

                if (!colunas[j].contains("VET") && j > 0 && colunas[j-1].contains("VET") && colunas[j-1].contains("[") && colunas[j-1].contains("]")){
                    String arrayName = colunas[j-1].substring(0, colunas[j-1].indexOf("["));
                    String arrayValue = colunas[j-1].substring(colunas[j-1].indexOf("[")+1, colunas[j-1].lastIndexOf("]"));
                    String fieldValue = colunas[j].replace(";","").replace("\"","").replace(" ","").replace("\r","");

                    if (arrayName.equals(MagicInfoFields.RARIDADE)){
                        if (listRaridade == null){
                            listRaridade = new ArrayList<>();
                        }

                        listRaridade.add(Integer.parseInt(arrayValue), Raridade.fromInt(Integer.parseInt(fieldValue)));
                    }

                    if (arrayName.equals(MagicInfoFields.NOME_OFICIAL)){
                        if (listNomeOficial == null){
                            listNomeOficial = new ArrayList<>();
                        }

                        listNomeOficial.add(Integer.parseInt(arrayValue), fieldValue);
                    }

                    if (arrayName.equals(MagicInfoFields.MENOR_PRECO)){
                        if (listMenorPreco == null){
                            listMenorPreco = new ArrayList<>();
                        }

                        listMenorPreco.add(Integer.parseInt(arrayValue), fieldValue);
                    }

                    if (arrayName.equals(MagicInfoFields.MEDIO_PRECO)){
                        if (listMedioPreco == null){
                            listMedioPreco = new ArrayList<>();
                        }

                        listMedioPreco.add(Integer.parseInt(arrayValue), fieldValue);
                    }

                    if (arrayName.equals(MagicInfoFields.MAIOR_PRECO)){
                        if (listMaiorPreco == null){
                            listMaiorPreco = new ArrayList<>();
                        }

                        listMaiorPreco.add(Integer.parseInt(arrayValue), fieldValue);
                    }

                    if (arrayName.equals(MagicInfoFields.IMAGEM)){
                        if (listImagem == null){
                            listImagem = new ArrayList<>();
                        }

                        listImagem.add(Integer.parseInt(arrayValue), fieldValue);
                    }
                }
            }
        }

        Log.d(TAG, "parse: finish parsing");
    }

    public List<MagicCardInfo> getListOfCards(){
        List<MagicCardInfo> list = new ArrayList<>();

        for (int i = 0; i < listImagem.size(); i++) {
            MagicCardInfo card = new MagicCardInfo();
            card.setImagem(listImagem.get(i));
            card.setRaridade(listRaridade.get(i));
            card.setNomeEdicaoOficial(listNomeOficial.get(i));
            card.setMenorPreco(listMenorPreco.get(i));
            card.setMedioPreco(listMedioPreco.get(i));
            card.setMaiorPreco(listMaiorPreco.get(i));

            list.add(card);
        }

        return list;
    }

    public List<String> getListRaridade() {
        return listRaridade;
    }

    public List<String> getListNomeOficial() {
        return listNomeOficial;
    }

    public List<String> getListMenorPreco() {
        return listMenorPreco;
    }

    public List<String> getListImagem() {
        return listImagem;
    }

    public List<String> getListMedioPreco() {
        return listMedioPreco;
    }

    public List<String> getListMaiorPreco() {
        return listMaiorPreco;
    }

    public enum Raridade{

        COMUM("Comun"), UNCOMUM("Uncomun"), RARE("Rare"), MYTHIC("Mythic");

        public String raridade;

        Raridade(String raridade) {
            this.raridade = raridade;
        }

        public static String fromInt(int raridade){
            for (int i = 0; i < values().length; i++) {
                if (values()[i].ordinal()+1 == raridade){
                    return values()[i].raridade;
                }
            }
            return null;
        }
    }
}

// VETiRaridade = new Array();
//         VETiID_Site = new Array();
//         VETsArtista = new Array();
//         VETsNomeOficial = new Array();
//         VETsNomePortugues = new Array();
//         VETsSigla = new Array();
//         VETprecoMaior = new Array();
//         VETprecoMedio = new Array();
//         VETprecoMenor = new Array();
//         VETdiarioMenor = new Array();
//         VETdiarioMedio = new Array();
//         VETdiarioMaior = new Array();
//         VETimage = new Array();
//         VETsimboloPath = new Array();
//         VETiRaridade[0] = "4";
//         VETiRaridade[1] = "4";
//         VETiID_Site[0] = "27";
//         VETiID_Site[1] = "136";
//         VETsArtista[0] = "Joseph Meehan";
//         VETsArtista[1] = "Chase Stone";
//         VETsNomeOficial[0] = "Masterpiece Series: Amonkhet Invocations";
//         VETsNomeOficial[1] = "Amonkhet";
//         VETsNomePortugues[0] = "";
//         VETsNomePortugues[1] = "";
//         VETsSigla[0] = "mpsakh";
//         VETsSigla[1] = "akh";
//         VETprecoMaior[0] = "229,95";
//         VETprecoMaior[1] = "99,75";
//         VETprecoMedio[0] = "229,95";
//         VETprecoMedio[1] = "72,31";
//         VETprecoMenor[0] = "229,95";
//         VETprecoMenor[1] = "69,00";
//         VETdiarioMenor[0] = "";
//         VETdiarioMenor[1] = "-9.10";
//         VETdiarioMedio[0] = "";
//         VETdiarioMedio[1] = "-0.11";
//         VETdiarioMaior[0] = "";
//         VETdiarioMaior[1] = "";
//         VETimage[0] = "https://magic.objects.liquidweb.services/en27mpsakh.jpg";
//         VETimage[1] = "https://magic.objects.liquidweb.services/pt136akh.jpg";
//         VETsimboloPath[0] = "//arquivos.objects.liquidweb.services/ed_mtg/MPSAKH_M.gif";
//         VETsimboloPath[1] = "//arquivos.objects.liquidweb.services/ed_mtg/AKH_M.gif";
