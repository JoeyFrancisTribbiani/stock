package com.yy.stock.adaptor.amazon.api.pojo.dto;


import com.yy.stock.adaptor.amazon.api.pojo.entity.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProductPresaleListDTO对象", description = "获取计划的商品列表")
public class ProductPresaleListDTO extends BasePageQuery {
    String groupid;
    String shopid;
    String sku;
    String msku;
    String marketplaceid;
    String asin;
    String owner;
    Boolean needplan;
    Date fromDate;
    Date toDate;
}
