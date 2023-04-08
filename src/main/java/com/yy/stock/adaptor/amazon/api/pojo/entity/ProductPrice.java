package com.yy.stock.adaptor.amazon.api.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_price")
public class ProductPrice extends BaseEntity {


    /**
     *
     */
    private static final long serialVersionUID = 4083893893436761564L;

    private String marketplaceid;

    private String asin;

    private Date byday;

    private String ptype;

    private BigDecimal landedAmount;

    private String landedCurrency;

    private BigDecimal listingAmount;

    private String listingCurrency;

    private BigDecimal shippingAmount;

    private String shippingCurrency;

    private Boolean isnewest;

    private String fulfillmentchannel;

    private String itemcondition;

    private String itemsubcondition;

    private String sellerid;

    private String sellersku;

}