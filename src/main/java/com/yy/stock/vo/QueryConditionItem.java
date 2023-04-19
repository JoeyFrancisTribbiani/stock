package com.yy.stock.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryConditionItem {
    private String field;
    private QueryMatchTypeEnum matchType;
    private String val;
}