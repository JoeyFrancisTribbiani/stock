/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.engine.driver.model.ChromePerformanceLogResponse;

/**
 * Auto-generated: 2023-04-17 17:28:10
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Params {

    private String frameId;
    private boolean hasExtraInfo;
    private String loaderId;
    private String requestId;
    private Response response;
    private double timestamp;
    private String type;

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public boolean getHasExtraInfo() {
        return hasExtraInfo;
    }

    public void setHasExtraInfo(boolean hasExtraInfo) {
        this.hasExtraInfo = hasExtraInfo;
    }

    public String getLoaderId() {
        return loaderId;
    }

    public void setLoaderId(String loaderId) {
        this.loaderId = loaderId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
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

}