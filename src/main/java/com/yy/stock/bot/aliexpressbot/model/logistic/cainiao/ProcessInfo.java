/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.aliexpressbot.model.logistic.cainiao;

import java.util.List;

/**
 * Auto-generated: 2023-03-05 13:31:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class ProcessInfo {

    private String progressStatus;
    private double progressRate;
    private String type;
    private List<ProgressPointList> progressPointList;

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public double getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(double progressRate) {
        this.progressRate = progressRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProgressPointList> getProgressPointList() {
        return progressPointList;
    }

    public void setProgressPointList(List<ProgressPointList> progressPointList) {
        this.progressPointList = progressPointList;
    }

}