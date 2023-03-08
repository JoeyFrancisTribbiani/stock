package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.entity.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AmzFinDataDTO对象", description = "获取商品财务信息")
public class AmzFinDataDTO extends BasePageQuery {

    String itemid;
    String marketplaceid;
    String groupid;
    String fromDate;
    String endDate;
    String sku;
    String osku;

}
