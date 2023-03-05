/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.aliexpressbot.model.logistic.aliexpress;

import java.util.Date;

/**
 * Auto-generated: 2023-03-05 12:19:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class EtaInfo {

    private Date deliveryMaxTimeString;
    private Date deliveryMinTimeString;
    private Date etaDesc;
    private boolean etaGapHighlight;
    private String etaNotifyCode;
    private AbsoluteTime absoluteTime;
    private RelativeTime relativeTime;

    public Date getDeliveryMaxTimeString() {
        return deliveryMaxTimeString;
    }

    public void setDeliveryMaxTimeString(Date deliveryMaxTimeString) {
        this.deliveryMaxTimeString = deliveryMaxTimeString;
    }

    public Date getDeliveryMinTimeString() {
        return deliveryMinTimeString;
    }

    public void setDeliveryMinTimeString(Date deliveryMinTimeString) {
        this.deliveryMinTimeString = deliveryMinTimeString;
    }

    public Date getEtaDesc() {
        return etaDesc;
    }

    public void setEtaDesc(Date etaDesc) {
        this.etaDesc = etaDesc;
    }

    public boolean getEtaGapHighlight() {
        return etaGapHighlight;
    }

    public void setEtaGapHighlight(boolean etaGapHighlight) {
        this.etaGapHighlight = etaGapHighlight;
    }

    public String getEtaNotifyCode() {
        return etaNotifyCode;
    }

    public void setEtaNotifyCode(String etaNotifyCode) {
        this.etaNotifyCode = etaNotifyCode;
    }

    public AbsoluteTime getAbsoluteTime() {
        return absoluteTime;
    }

    public void setAbsoluteTime(AbsoluteTime absoluteTime) {
        this.absoluteTime = absoluteTime;
    }

    public RelativeTime getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(RelativeTime relativeTime) {
        this.relativeTime = relativeTime;
    }

}