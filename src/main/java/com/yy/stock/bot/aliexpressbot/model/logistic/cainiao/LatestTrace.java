/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.aliexpressbot.model.logistic.cainiao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatestTrace {

    private long time;
    private String timeStr;
    private String desc;
    private String standerdDesc;
    private String descTitle;
    private String timeZone;
    private String actionCode;
    private Group group;

}