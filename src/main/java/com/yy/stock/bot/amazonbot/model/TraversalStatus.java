package com.yy.stock.bot.amazonbot.model;

import lombok.Getter;
import lombok.Setter;

// 采集链接下产品的进度状态：待采集、采集中、采集完成
public enum TraversalStatus {
    Pending("待采集"),
    Progressing("采集中"),
    Done("采集完成");
    private String value;
    TraversalStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
