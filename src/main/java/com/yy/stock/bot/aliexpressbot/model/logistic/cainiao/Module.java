/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.aliexpressbot.model.logistic.cainiao;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-03-05 13:31:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Module {
    private String destCity;
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
}