package com.yy.stock.adaptor.amazon.api.pojo.vo;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductInfoListVo {

    String id;

    Integer willfulfillable;

    String sku;

    String asin;

    String color;

    String name;

    String shopid;

    String msku;

    String upc;

    String image;

    String brand;

    String price;

    String boxnum;

    String fulfillable;

    String MOQ;

    String supplier;

    String issfg;

    String remark;

    String operator;

    Date opttime;

    String purchaseUrl;

    String delivery_cycle;

    BigDecimal length;

    BigDecimal width;

    BigDecimal height;

    BigDecimal weight;

    String guidance;

    Integer quantity;


}
