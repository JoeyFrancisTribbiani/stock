/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.engine.driver.model.ChromePerformanceLogResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityDetails {

    private int certificateId;
    private String certificateTransparencyCompliance;
    private String cipher;
    private boolean encryptedClientHello;
    private String issuer;
    private String keyExchange;
    private String keyExchangeGroup;
    private String protocol;
    private int serverSignatureAlgorithm;
    private String subjectName;
    private long validFrom;
    private long validTo;

}