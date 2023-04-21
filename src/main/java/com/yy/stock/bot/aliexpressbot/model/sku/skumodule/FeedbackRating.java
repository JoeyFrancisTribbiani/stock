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
public class FeedbackRating {

    private String averageStar;
    private String averageStarRage;
    private boolean display;
    private String evarageStar;
    private String evarageStarRage;
    private int fiveStarNum;
    private String fiveStarRate;
    private int fourStarNum;
    private String fourStarRate;
    private int oneStarNum;
    private String oneStarRate;
    private String positiveRate;
    private int threeStarNum;
    private String threeStarRate;
    private int totalValidNum;
    private int trialReviewNum;
    private int twoStarNum;
    private String twoStarRate;

    public String getAverageStar() {
        return averageStar;
    }

    public void setAverageStar(String averageStar) {
        this.averageStar = averageStar;
    }

    public String getAverageStarRage() {
        return averageStarRage;
    }

    public void setAverageStarRage(String averageStarRage) {
        this.averageStarRage = averageStarRage;
    }

    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getEvarageStar() {
        return evarageStar;
    }

    public void setEvarageStar(String evarageStar) {
        this.evarageStar = evarageStar;
    }

    public String getEvarageStarRage() {
        return evarageStarRage;
    }

    public void setEvarageStarRage(String evarageStarRage) {
        this.evarageStarRage = evarageStarRage;
    }

    public int getFiveStarNum() {
        return fiveStarNum;
    }

    public void setFiveStarNum(int fiveStarNum) {
        this.fiveStarNum = fiveStarNum;
    }

    public String getFiveStarRate() {
        return fiveStarRate;
    }

    public void setFiveStarRate(String fiveStarRate) {
        this.fiveStarRate = fiveStarRate;
    }

    public int getFourStarNum() {
        return fourStarNum;
    }

    public void setFourStarNum(int fourStarNum) {
        this.fourStarNum = fourStarNum;
    }

    public String getFourStarRate() {
        return fourStarRate;
    }

    public void setFourStarRate(String fourStarRate) {
        this.fourStarRate = fourStarRate;
    }

    public int getOneStarNum() {
        return oneStarNum;
    }

    public void setOneStarNum(int oneStarNum) {
        this.oneStarNum = oneStarNum;
    }

    public String getOneStarRate() {
        return oneStarRate;
    }

    public void setOneStarRate(String oneStarRate) {
        this.oneStarRate = oneStarRate;
    }

    public String getPositiveRate() {
        return positiveRate;
    }

    public void setPositiveRate(String positiveRate) {
        this.positiveRate = positiveRate;
    }

    public int getThreeStarNum() {
        return threeStarNum;
    }

    public void setThreeStarNum(int threeStarNum) {
        this.threeStarNum = threeStarNum;
    }

    public String getThreeStarRate() {
        return threeStarRate;
    }

    public void setThreeStarRate(String threeStarRate) {
        this.threeStarRate = threeStarRate;
    }

    public int getTotalValidNum() {
        return totalValidNum;
    }

    public void setTotalValidNum(int totalValidNum) {
        this.totalValidNum = totalValidNum;
    }

    public int getTrialReviewNum() {
        return trialReviewNum;
    }

    public void setTrialReviewNum(int trialReviewNum) {
        this.trialReviewNum = trialReviewNum;
    }

    public int getTwoStarNum() {
        return twoStarNum;
    }

    public void setTwoStarNum(int twoStarNum) {
        this.twoStarNum = twoStarNum;
    }

    public String getTwoStarRate() {
        return twoStarRate;
    }

    public void setTwoStarRate(String twoStarRate) {
        this.twoStarRate = twoStarRate;
    }

}