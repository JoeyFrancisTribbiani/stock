/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.engine.driver.model.ChromePerformanceLogRequestWillBeSentExtraInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

/**
 * Auto-generated: 2023-04-17 18:35:7
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Params {

    //    private List<AssociatedCookies> associatedCookies;
    private ClientSecurityState clientSecurityState;
    private ConnectTiming connectTiming;
    private Map<String, String> headers;
    private String requestId;
}