package com.yy.stock.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

//@Data
//@AllArgsConstructor
//public class OrderItemAdaptorInfoDTO {
//    public Date buydate();
//    public String itemstatus();
//    public String orderstatus();
//    public String orderid();
//    public BigInteger groupid();
//    public String channel();
//    public BigDecimal orderprice();
//    public BigDecimal itemprice();
//    public Integer quantity();
//    public String image();
//    public String sku();
//    public String asin();
//    public String groupname();
//    public String color();
//    public String name();
//    public String id();
//    public String market();
//    public String currency();
//    public String shipservice();
//    public String city();
//    public BigDecimal shipfee();
//    public BigDecimal shipdiscount();
//    public BigDecimal itemdiscount();
//    public BigInteger authid();
//    public String remark();
//    public boolean isbusiness();
//    public String marketplaceId();
//    public String marketname();
//    public String region();
//    public String feedstatus();
//    public BigInteger feedid();
//
//    public OrderItemAdaptorInfoDTO() {
//    }

public interface OrderItemAdaptorInfoDTO {
    Date getBuydate();

    String getItemstatus();

    String getOrderstatus();

    String getOrderid();

    BigInteger getGroupid();

    String getChannel();

    BigDecimal getOrderprice();

    BigDecimal getItemprice();

    Integer getQuantity();

    String getImage();

    String getSku();

    String getAsin();

    String getGroupname();

    String getColor();

    String getName();

    String getId();

    String getMarket();

    String getCurrency();

    String getShipservice();

    String getCity();

    BigDecimal getShipfee();

    BigDecimal getShipdiscount();

    BigDecimal getItemdiscount();

    BigInteger getAuthid();

    String getRemark();

    boolean getIsbusiness();

    String getMarketplaceId();

    String getMarketname();

    String getRegion();

    String getFeedstatus();

    BigInteger getFeedid();


}