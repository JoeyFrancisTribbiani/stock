/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.engine.driver.model.ChromePerformanceLogRequestWillBeSent;

/**
 * Auto-generated: 2023-04-17 14:50:51
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Params {

    private String documentURL;
    private String frameId;
    private boolean hasUserGesture;
    private Initiator initiator;
    private String loaderId;
    private boolean redirectHasExtraInfo;
    private Request request;
    private String requestId;
    private double timestamp;
    private String type;
    private double wallTime;

    public String getDocumentURL() {
        return documentURL;
    }

    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
    }

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public boolean getHasUserGesture() {
        return hasUserGesture;
    }

    public void setHasUserGesture(boolean hasUserGesture) {
        this.hasUserGesture = hasUserGesture;
    }

    public Initiator getInitiator() {
        return initiator;
    }

    public void setInitiator(Initiator initiator) {
        this.initiator = initiator;
    }

    public String getLoaderId() {
        return loaderId;
    }

    public void setLoaderId(String loaderId) {
        this.loaderId = loaderId;
    }

    public boolean getRedirectHasExtraInfo() {
        return redirectHasExtraInfo;
    }

    public void setRedirectHasExtraInfo(boolean redirectHasExtraInfo) {
        this.redirectHasExtraInfo = redirectHasExtraInfo;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWallTime() {
        return wallTime;
    }

    public void setWallTime(double wallTime) {
        this.wallTime = wallTime;
    }

}