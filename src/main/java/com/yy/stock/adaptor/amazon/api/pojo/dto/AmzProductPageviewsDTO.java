package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AmzProductPageviewsDTO对象", description = "获取商品Business列表")
public class AmzProductPageviewsDTO extends BasePageQuery {
    @ApiModelProperty(value = "站点")
    String marketplaceid;

    @ApiModelProperty(value = "公司ID[自动填充]")
    String shopid;

    @ApiModelProperty(value = "店铺ID")
    String groupid;

    @ApiModelProperty(value = "authid[自动填充]")
    String amazonauthid;

    @ApiModelProperty(value = "查询内容")
    String search;

    @ApiModelProperty(value = "开始日期")
    String startDate;

    @ApiModelProperty(value = "开始日期")
    String endDate;
}
