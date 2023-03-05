/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.aliexpressbot.model.logistic.aliexpress;

import java.util.List;

/**
 * Auto-generated: 2023-03-05 12:19:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class ProgressInfo {

    private double progressRate;
    private List<ProgressPoints> progressPoints;
    private String progressStatus;

    public double getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(double progressRate) {
        this.progressRate = progressRate;
    }

    public List<ProgressPoints> getProgressPoints() {
        return progressPoints;
    }

    public void setProgressPoints(List<ProgressPoints> progressPoints) {
        this.progressPoints = progressPoints;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

}