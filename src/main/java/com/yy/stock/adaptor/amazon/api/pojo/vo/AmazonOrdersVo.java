package com.yy.stock.adaptor.amazon.api.pojo.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AmazonOrdersVo {

    Long buydate;
    Boolean buyerrequestedcancel;
    String buyercancelreason;


    String itemstatus;

    String orderstatus;

    String orderid;

    String groupid;

    String channel;

    BigDecimal orderprice;

    BigDecimal itemprice;

    Integer quantity;

    String image;

    String sku;

    String asin;

    String groupname;

    String color;

    String name;

    String id;

    String market;

    String currency;

    String shipservice;

    String city;

    BigDecimal shipfee;

    BigDecimal shipdiscount;

    BigDecimal itemdiscount;

    String authid;

    String remark;

    String isbusiness;

    String marketplaceId;

    String marketname;

    String region;

    String feedstatus;

    String feedid;

    public String getKey() {
        return orderid + "-" + sku;
    }
}
