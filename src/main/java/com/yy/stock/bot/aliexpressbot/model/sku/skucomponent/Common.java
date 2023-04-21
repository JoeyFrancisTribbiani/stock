/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Common {

    private String recommendationsFrom;
    private String sharePc;
    private String skuEditSelection;
    private String description;
    private String specification;
    private String skuSelections;
    private String feedback;
    private String youMayAlsoLike;
    private String outOfStock;
    private String topSellingsFrom;
    private String similarRecommond;
    private String youMayAlsoLikeSeeMore;
    private String qanda;

    public String getRecommendationsFrom() {
        return recommendationsFrom;
    }

    public void setRecommendationsFrom(String recommendationsFrom) {
        this.recommendationsFrom = recommendationsFrom;
    }

    public String getSharePc() {
        return sharePc;
    }

    public void setSharePc(String sharePc) {
        this.sharePc = sharePc;
    }

    public String getSkuEditSelection() {
        return skuEditSelection;
    }

    public void setSkuEditSelection(String skuEditSelection) {
        this.skuEditSelection = skuEditSelection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSkuSelections() {
        return skuSelections;
    }

    public void setSkuSelections(String skuSelections) {
        this.skuSelections = skuSelections;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getYouMayAlsoLike() {
        return youMayAlsoLike;
    }

    public void setYouMayAlsoLike(String youMayAlsoLike) {
        this.youMayAlsoLike = youMayAlsoLike;
    }

    public String getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(String outOfStock) {
        this.outOfStock = outOfStock;
    }

    public String getTopSellingsFrom() {
        return topSellingsFrom;
    }

    public void setTopSellingsFrom(String topSellingsFrom) {
        this.topSellingsFrom = topSellingsFrom;
    }

    public String getSimilarRecommond() {
        return similarRecommond;
    }

    public void setSimilarRecommond(String similarRecommond) {
        this.similarRecommond = similarRecommond;
    }

    public String getYouMayAlsoLikeSeeMore() {
        return youMayAlsoLikeSeeMore;
    }

    public void setYouMayAlsoLikeSeeMore(String youMayAlsoLikeSeeMore) {
        this.youMayAlsoLikeSeeMore = youMayAlsoLikeSeeMore;
    }

    public String getQanda() {
        return qanda;
    }

    public void setQanda(String qanda) {
        this.qanda = qanda;
    }

}