/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

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
public class FeedbackModule {

    private long companyId;
    private String feedbackServer;
    private I18nMap i18nMap;
    private int id;
    private String name;
    private long productId;
    private long sellerAdminSeq;
    private int trialReviewNum;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }


    public String getFeedbackServer() {
        return feedbackServer;
    }

    public void setFeedbackServer(String feedbackServer) {
        this.feedbackServer = feedbackServer;
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

    public long getSellerAdminSeq() {
        return sellerAdminSeq;
    }

    public void setSellerAdminSeq(long sellerAdminSeq) {
        this.sellerAdminSeq = sellerAdminSeq;
    }

    public int getTrialReviewNum() {
        return trialReviewNum;
    }

    public void setTrialReviewNum(int trialReviewNum) {
        this.trialReviewNum = trialReviewNum;
    }

}