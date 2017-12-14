package com.android.josesantos.magiccardsinfo.data.ligamagic;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by josesantos on 24/11/17.
 */

public class LojasInfoParser {

    public List<LojaInfo> lojasInfo = new ArrayList<>();

    private static String CURRENCY = "R$";
    private static String QUANTITY = "unid";
    private static String IR_LOJA = "Ir à loja";

    private static final String TAG = "LojasInfoParser";

    // TODO: 25/11/17 improve method speed
    public void parse(Document doc){

        Elements lojasElements = doc.getElementsByTag("tr");

        for (Element element: lojasElements) {

            if (element.toString().contains("banner-loja")){
                Log.d(TAG, "parse: "+element);

                LojaInfo lojaInfo = new LojaInfo();

                for (Element element1 : element.children()) {
                    Log.d(TAG, "children: "+element1.text());

                    if (isNome(element1)){
                        lojaInfo.setNome(getNome(element1));
                    }

                    if(isEdition(element1)){
                        lojaInfo.setEdition(element1.text());
                    }

                    if (isOnlyPrice(element1)){
                        lojaInfo.setPrice(getPrice(element1));
                    }else if (isPriceAndPromoPrice(element1)){
                        lojaInfo.setPrice(getPrice(element1));
                        lojaInfo.setPromoPrice(getPromoPrice(element1));
                    }

                    if (isQuantity(element1)){
                        lojaInfo.setQtd(element1.text());
                    }
                }

                lojasInfo.add(lojaInfo);

            }
        }
    }

    private boolean isPriceAndPromoPrice(Element element1) {
        if (element1.text().contains(CURRENCY) && element1.text().split(" ").length == 4){
            return true;
        }

        return false;
    }

    private boolean isOnlyPrice(Element element1) {
        if (element1.text().contains(CURRENCY) && element1.text().split(" ").length == 2){
            return true;
        }

        return false;
    }

    private String getPrice(Element element){
        if (element.text().contains(CURRENCY)){

            List<String> completePrices = Arrays.asList(element.text().split(" "));
            List<String> prices = new ArrayList<>();

            for (String p : completePrices) {
                if (!p.contains(CURRENCY)) {
                    prices.add(p);
                }
            }

            return prices.get(0);
        }

        return null;
    }

    private String getPromoPrice(Element element){
        if (element.text().contains(CURRENCY)){

            List<String> completePrices = Arrays.asList(element.text().split(" "));
            List<String> prices = new ArrayList<>();

            for (String p : completePrices) {
                if (!p.contains(CURRENCY)) {
                    prices.add(p);
                }
            }

            return prices.get(1);
        }

        return null;
    }

    private boolean isQuantity(Element element1) {
        return element1.text().contains(QUANTITY);
    }

    private boolean isEdition(Element element1) {
        return !element1.text().equals("")
                && !element1.text().contains(CURRENCY)
                && !element1.text().contains(QUANTITY)
                && !element1.text().contains(IR_LOJA);
    }

    private boolean isNome(Element element){
        return element.toString().contains("banner-loja");
    }

    private String getNome(Element element){
        int firstIndex = element.toString().indexOf("title");

        String firstHalf = element.toString().substring(firstIndex, element.toString().length());

        return firstHalf.substring(firstHalf.indexOf("\"")+1,firstHalf.lastIndexOf("\""));
    }

    public List<LojaInfo> getLojasInfo() {
        return lojasInfo;
    }
}
