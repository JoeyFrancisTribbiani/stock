/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:4:52
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
public class ImageModule {

    private Features features;
    private I18nMap i18nMap;
    private int id;
    private List<String> imagePathList;
    private String name;
    private List<String> summImagePathList;

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

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