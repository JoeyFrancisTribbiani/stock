package com.yy.stock.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * 通用查询条件
 */
@Accessors(chain = true)
@Getter
@Setter
public class QueryConditions extends ArrayList<QueryConditionItem> {
}