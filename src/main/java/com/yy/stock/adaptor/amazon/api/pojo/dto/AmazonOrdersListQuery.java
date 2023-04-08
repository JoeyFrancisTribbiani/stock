package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.entity.AmazonGroup;
import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AmazonOrdersListQuery extends BasePageQuery {


    String startDate;

    String endDate;

    String groupid;

    String amazonAuthId;

    String marketplaceid;

    List<AmazonGroup> groupList;

    String orderid;

    String asin;

    String sku;

    String channel;

    String status;

    String color;

    String searchtype;

    String pointname;

    String search;

    String isbusiness;

    String shopid;

    String isarchive = "false";

}
