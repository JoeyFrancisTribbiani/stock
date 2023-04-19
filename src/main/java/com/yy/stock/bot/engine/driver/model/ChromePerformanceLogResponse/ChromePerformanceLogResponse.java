/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.engine.driver.model.ChromePerformanceLogResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Auto-generated: 2023-04-17 17:28:10
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChromePerformanceLogResponse {

    private Message message;
    private String webview;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getWebview() {
        return webview;
    }

    public void setWebview(String webview) {
        this.webview = webview;
    }

}