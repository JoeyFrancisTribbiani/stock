package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_price_locked")
public class ProductPriceLocked {

    @TableId(value = "pid")
    private String pid;

    @TableField(value = "price")
    private BigDecimal price;

    @TableField(value = "starttime")
    private Date starttime;

    @TableField(value = "endtime")
    private Date endtime;

    @TableField(value = "disable")
    private Boolean disable;

    @TableField(value = "operator")
    private String operator;

    @TableField(value = "opttime")
    private Date opttime;
}