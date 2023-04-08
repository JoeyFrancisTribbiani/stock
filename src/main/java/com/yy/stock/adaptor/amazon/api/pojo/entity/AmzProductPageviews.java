package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_amz_product_pageviews_download")
public class AmzProductPageviews extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @TableField("amazonAuthid")
    private BigInteger amazonAuthid;

    private String marketplaceid;

    @TableField("SKU")
    private String sku;

    private Date byday;

    private String parentAsin;

    private String childAsin;

    @TableField("Sessions")
    private Integer sessions;

    @TableField("Session_Percentage")
    private BigDecimal sessionPercentage;

    @TableField("Page_Views")
    private Integer pageViews;

    @TableField("Page_Views_Percentage")
    private BigDecimal pageViewsPercentage;

    @TableField("Buy_Box_Percentage")
    private BigDecimal buyBoxPercentage;

    @TableField("Units_Ordered")
    private Integer unitsOrdered;

    @TableField("Units_Ordered_B2B")
    private Integer unitsOrderedB2b;

    @TableField("Unit_Session_Percentage")
    private BigDecimal unitSessionPercentage;

    @TableField("Unit_Session_Percentage_B2B")
    private BigDecimal unitSessionPercentageB2b;

    @TableField("Ordered_Product_Sales")
    private BigDecimal orderedProductSales;

    @TableField("Ordered_Product_Sales_B2B")
    private BigDecimal orderedProductSalesB2b;

    @TableField("Total_Order_Items")
    private Integer totalOrderItems;

    @TableField("Total_Order_Items_B2B")
    private Integer totalOrderItemsB2b;

    private Date opttime;
}
