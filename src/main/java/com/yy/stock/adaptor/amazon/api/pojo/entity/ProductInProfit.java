package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@TableName("t_product_in_profit")
public class ProductInProfit implements Serializable {


    private static final long serialVersionUID = -4059575531484385737L;

    @TableId(value = "pid")
    private String pid;

    @TableField(value = "profit_week")
    private BigDecimal profitWeek;

    @TableField(value = "margin_week")
    private BigDecimal marginWeek;

    @TableField(value = "lastupdate")
    private Date lastupdate;

    @TableField(value = "planName")
    private String planName;

    @TableField(value = "dimensions")
    private String dimensions;

    @TableField(value = "weight")
    private String weight;

    @TableField(value = "shipmentfee")
    private BigDecimal shipmentfee;

    @TableField(value = "othersfee")
    private BigDecimal othersfee;

    @TableField(value = "costDetail")
    private String costdetail;

    @TableField(value = "margin")
    private BigDecimal margin;


}