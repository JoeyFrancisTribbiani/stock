/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.adaptor.seleniumgrid.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogchromeOptions {
    private List<String> args;
    private String debuggerAddress;
}