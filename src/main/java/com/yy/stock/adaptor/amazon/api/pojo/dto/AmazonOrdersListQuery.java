package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.entity.AmazonGroup;
import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmazonOrdersListQuery extends BasePageQuery {


    @ApiModelProperty(value = "开始日期")
    String startDate;

    @ApiModelProperty(value = "结束日期")
    String endDate;

    @ApiModelProperty(value = "店铺ID[全选时为all]")
    String groupid;

    @ApiModelProperty(value = "AuthID[系统填充]")
    String amazonAuthId;

    @ApiModelProperty(value = "站点ID[系统填充]")
    String marketplaceid;

    @ApiModelProperty(value = "店铺List[系统填充]")
    List<AmazonGroup> groupList;

    @ApiModelProperty(value = "orderid[系统填充]")
    String orderid;

    @ApiModelProperty(value = "asin[系统填充]")
    String asin;

    @ApiModelProperty(value = "sku[系统填充]")
    String sku;

    @ApiModelProperty(value = "发货渠道,值:[Amazon,Merchant]")
    String channel;

    @ApiModelProperty(value = "订单状态[Unshipped,Shipped,Cancelled,Pending]")
    String status;

    @ApiModelProperty(value = "产品颜色")
    String color;

    @ApiModelProperty(value = "查询类型[sku,asin,number]")
    String searchtype;

    @ApiModelProperty(value = "站点[Amazon.ca,Amazon.com.....]")
    String pointname;

    @ApiModelProperty(value = "查询内容")
    String search;

    @ApiModelProperty(value = "客户类型['true','false']")
    String isbusiness;

    @ApiModelProperty(value = "公司ID[系统填充]")
    String shopid;

    @ApiModelProperty(value = "[系统填充]")
    String isarchive = "false";

}
