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
public class TrackingNumbersInfo {

    private String logisticsNo;
    private List<Website> website;
    private String serviceIconUrl;
    private String present;
    private String serviceName;
    private String desc;

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public List<Website> getWebsite() {
        return website;
    }

    public void setWebsite(List<Website> website) {
        this.website = website;
    }

    public String getServiceIconUrl() {
        return serviceIconUrl;
    }

    public void setServiceIconUrl(String serviceIconUrl) {
        this.serviceIconUrl = serviceIconUrl;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}