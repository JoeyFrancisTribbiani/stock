/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.adaptor.seleniumgrid.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Nodes {
    private String id;
    private String uri;
    private int maxSessions;
    private OsInfo osInfo;
    private int heartbeatPeriod;
    private String availability;
    private String version;
    private List<Slots> slots;
}