/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ImageModule {

    private I18nMap i18nMap;
    private int id;
    private List<String> imagePathList;
    private String name;
    private List<String> summImagePathList;


    public I18nMap getI18nMap() {
        return i18nMap;
    }

    public void setI18nMap(I18nMap i18nMap) {
        this.i18nMap = i18nMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImagePathList() {
        return imagePathList;
    }

    public void setImagePathList(List<String> imagePathList) {
        this.imagePathList = imagePathList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSummImagePathList() {
        return summImagePathList;
    }

    public void setSummImagePathList(List<String> summImagePathList) {
        this.summImagePathList = summImagePathList;
    }

}