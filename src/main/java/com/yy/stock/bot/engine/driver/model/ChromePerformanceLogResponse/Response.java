/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.engine.driver.model.ChromePerformanceLogResponse;

import lombok.Data;

import java.util.Map;


/**
 * Auto-generated: 2023-04-17 17:28:10
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Response {
    private String alternateProtocolUsage;
    private int connectionId;
    private boolean connectionReused;
    private int encodedDataLength;
    private boolean fromDiskCache;
    private boolean fromPrefetchCache;
    private boolean fromServiceWorker;
    private Map<String, String> headers;
    private String mimeType;
    private String protocol;
    private String remoteIPAddress;
    private int remotePort;
    private double responseTime;
    private SecurityDetails securityDetails;
    private String securityState;
    private int status;
    private String statusText;
    private Timing timing;
    private String url;
}