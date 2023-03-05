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
public class Module {

    private String mailNo;
    private String originCountry;
    private String destCountry;
    private String status;
    private String statusDesc;
    private String mailNoSource;
    private ProcessInfo processInfo;
    private GlobalEtaInfo globalEtaInfo;
    private LatestTrace latestTrace;
    private List<DetailList> detailList;

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getDestCountry() {
        return destCountry;
    }

    public void setDestCountry(String destCountry) {
        this.destCountry = destCountry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getMailNoSource() {
        return mailNoSource;
    }

    public void setMailNoSource(String mailNoSource) {
        this.mailNoSource = mailNoSource;
    }

    public ProcessInfo getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(ProcessInfo processInfo) {
        this.processInfo = processInfo;
    }

    public GlobalEtaInfo getGlobalEtaInfo() {
        return globalEtaInfo;
    }

    public void setGlobalEtaInfo(GlobalEtaInfo globalEtaInfo) {
        this.globalEtaInfo = globalEtaInfo;
    }

    public LatestTrace getLatestTrace() {
        return latestTrace;
    }

    public void setLatestTrace(LatestTrace latestTrace) {
        this.latestTrace = latestTrace;
    }

    public List<DetailList> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailList> detailList) {
        this.detailList = detailList;
    }

}