package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProductSalesPlanDTO extends BasePageQuery {
    String groupid;
    String warehouseid;
}
