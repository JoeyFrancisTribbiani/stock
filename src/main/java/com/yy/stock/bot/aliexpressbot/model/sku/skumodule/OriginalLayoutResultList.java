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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OriginalLayoutResultList {

    private List<AdditionLayout> additionLayout;
    private BizData bizData;
    private List<List<ContentLayout>> contentLayout;
    private List<String> deliveryOptionPanelDisplayList;
    private List<String> detailFirstScreenDisplayList;
    private DxTemplateInfo dxTemplateInfo;
    private long finalPatternId;
    private List<String> skuSwitchDisplayList;
    private List<List<TitleLayout>> titleLayout;

    public List<AdditionLayout> getAdditionLayout() {
        return additionLayout;
    }

    public void setAdditionLayout(List<AdditionLayout> additionLayout) {
        this.additionLayout = additionLayout;
    }

    public BizData getBizData() {
        return bizData;
    }

    public void setBizData(BizData bizData) {
        this.bizData = bizData;
    }

    public List<List<ContentLayout>> getContentLayout() {
        return contentLayout;
    }

    public void setContentLayout(List<List<ContentLayout>> contentLayout) {
        this.contentLayout = contentLayout;
    }

    public List<String> getDeliveryOptionPanelDisplayList() {
        return deliveryOptionPanelDisplayList;
    }

    public void setDeliveryOptionPanelDisplayList(List<String> deliveryOptionPanelDisplayList) {
        this.deliveryOptionPanelDisplayList = deliveryOptionPanelDisplayList;
    }

    public List<String> getDetailFirstScreenDisplayList() {
        return detailFirstScreenDisplayList;
    }

    public void setDetailFirstScreenDisplayList(List<String> detailFirstScreenDisplayList) {
        this.detailFirstScreenDisplayList = detailFirstScreenDisplayList;
    }

    public DxTemplateInfo getDxTemplateInfo() {
        return dxTemplateInfo;
    }

    public void setDxTemplateInfo(DxTemplateInfo dxTemplateInfo) {
        this.dxTemplateInfo = dxTemplateInfo;
    }

    public long getFinalPatternId() {
        return finalPatternId;
    }

    public void setFinalPatternId(long finalPatternId) {
        this.finalPatternId = finalPatternId;
    }

    public List<String> getSkuSwitchDisplayList() {
        return skuSwitchDisplayList;
    }

    public void setSkuSwitchDisplayList(List<String> skuSwitchDisplayList) {
        this.skuSwitchDisplayList = skuSwitchDisplayList;
    }

    public List<List<TitleLayout>> getTitleLayout() {
        return titleLayout;
    }

    public void setTitleLayout(List<List<TitleLayout>> titleLayout) {
        this.titleLayout = titleLayout;
    }

}