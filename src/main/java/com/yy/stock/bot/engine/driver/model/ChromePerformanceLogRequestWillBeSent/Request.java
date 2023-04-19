/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.engine.driver.model.ChromePerformanceLogRequestWillBeSent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Auto-generated: 2023-04-17 14:50:51
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    private boolean hasPostData;
    private Map<String, String> headers;
    private String initialPriority;
    private boolean isSameSite;
    private String method;
    private String mixedContentType;
    private String postData;
    //    private List<PostDataEntries> postDataEntries;
    private String referrerPolicy;
    private String url;

}