
package com.android.josesantos.magiccardsinfo.data.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MagicApiCard {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("manaCost")
    @Expose
    private String manaCost;
    @SerializedName("cmc")
    @Expose
    private Integer cmc;
    @SerializedName("colors")
    @Expose
    private List<String> colors = null;
    @SerializedName("colorIdentity")
    @Expose
    private List<String> colorIdentity = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("subtypes")
    @Expose
    private List<String> subtypes = null;
    @SerializedName("rarity")
    @Expose
    private String rarity;
    @SerializedName("set")
    @Expose
    private String set;
    @SerializedName("setName")
    @Expose
    private String setName;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("flavor")
    @Expose
    private String flavor;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("power")
    @Expose
    private String power;
    @SerializedName("toughness")
    @Expose
    private String toughness;
    @SerializedName("layout")
    @Expose
    private String layout;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("foreignNames")
    @Expose
    private List<ForeignName> foreignNames = null;
    @SerializedName("printings")
    @Expose
    private List<String> printings = null;
    @SerializedName("legalities")
    @Expose
    private List<Legality> legalities = null;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public Integer getCmc() {
        return cmc;
    }

    public void setCmc(Integer cmc) {
        this.cmc = cmc;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getColorIdentity() {
        return colorIdentity;
    }

    public void setColorIdentity(List<String> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(List<String> subtypes) {
        this.subtypes = subtypes;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<ForeignName> getForeignNames() {
        return foreignNames;
    }

    public void setForeignNames(List<ForeignName> foreignNames) {
        this.foreignNames = foreignNames;
    }

    public List<String> getPrintings() {
        return printings;
    }

    public void setPrintings(List<String> printings) {
        this.printings = printings;
    }

    public List<Legality> getLegalities() {
        return legalities;
    }

    public void setLegalities(List<Legality> legalities) {
        this.legalities = legalities;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
