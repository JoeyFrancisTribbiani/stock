/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CrossLinkModule {

    private List<BreadCrumbPathList> breadCrumbPathList;
    private List<CrossLinkGroupList> crossLinkGroupList;
    private I18nMap i18nMap;
    private int id;
    private String name;
    private long productId;

    public List<BreadCrumbPathList> getBreadCrumbPathList() {
        return breadCrumbPathList;
    }

    public void setBreadCrumbPathList(List<BreadCrumbPathList> breadCrumbPathList) {
        this.breadCrumbPathList = breadCrumbPathList;
    }

    public List<CrossLinkGroupList> getCrossLinkGroupList() {
        return crossLinkGroupList;
    }

    public void setCrossLinkGroupList(List<CrossLinkGroupList> crossLinkGroupList) {
        this.crossLinkGroupList = crossLinkGroupList;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

}