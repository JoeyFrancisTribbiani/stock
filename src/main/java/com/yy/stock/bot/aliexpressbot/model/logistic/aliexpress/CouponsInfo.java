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
public class CouponsInfo {

    private List<Long> tradeOrderLineIdList;
    private String shipToCountry;
    private String mailNo;

    public List<Long> getTradeOrderLineIdList() {
        return tradeOrderLineIdList;
    }

    public void setTradeOrderLineIdList(List<Long> tradeOrderLineIdList) {
        this.tradeOrderLineIdList = tradeOrderLineIdList;
    }

    public String getShipToCountry() {
        return shipToCountry;
    }

    public void setShipToCountry(String shipToCountry) {
        this.shipToCountry = shipToCountry;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

}