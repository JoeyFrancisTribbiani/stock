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
public class Packages {

    private TradeOrderInfo tradeOrderInfo;
    private String logisticsNo;
    private MultiLangDesc multiLangDesc;
    private HeadCardInfo headCardInfo;
    private CouponsInfo couponsInfo;
    private long sentTime;
    private Notice notice;
    private EtaInfo etaInfo;
    private ProgressInfo progressInfo;
    private ReceiverInfo receiverInfo;
    private TrackInfo trackInfo;
    private List<TrackingNumbersInfo> trackingNumbersInfo;
    private boolean buyer;
    private String status;

    public TradeOrderInfo getTradeOrderInfo() {
        return tradeOrderInfo;
    }

    public void setTradeOrderInfo(TradeOrderInfo tradeOrderInfo) {
        this.tradeOrderInfo = tradeOrderInfo;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public MultiLangDesc getMultiLangDesc() {
        return multiLangDesc;
    }

    public void setMultiLangDesc(MultiLangDesc multiLangDesc) {
        this.multiLangDesc = multiLangDesc;
    }

    public HeadCardInfo getHeadCardInfo() {
        return headCardInfo;
    }

    public void setHeadCardInfo(HeadCardInfo headCardInfo) {
        this.headCardInfo = headCardInfo;
    }

    public CouponsInfo getCouponsInfo() {
        return couponsInfo;
    }

    public void setCouponsInfo(CouponsInfo couponsInfo) {
        this.couponsInfo = couponsInfo;
    }

    public long getSentTime() {
        return sentTime;
    }

    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public EtaInfo getEtaInfo() {
        return etaInfo;
    }

    public void setEtaInfo(EtaInfo etaInfo) {
        this.etaInfo = etaInfo;
    }

    public ProgressInfo getProgressInfo() {
        return progressInfo;
    }

    public void setProgressInfo(ProgressInfo progressInfo) {
        this.progressInfo = progressInfo;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public TrackInfo getTrackInfo() {
        return trackInfo;
    }

    public void setTrackInfo(TrackInfo trackInfo) {
        this.trackInfo = trackInfo;
    }

    public List<TrackingNumbersInfo> getTrackingNumbersInfo() {
        return trackingNumbersInfo;
    }

    public void setTrackingNumbersInfo(List<TrackingNumbersInfo> trackingNumbersInfo) {
        this.trackingNumbersInfo = trackingNumbersInfo;
    }

    public boolean getBuyer() {
        return buyer;
    }

    public void setBuyer(boolean buyer) {
        this.buyer = buyer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}