package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_follow")
public class ProductFollow extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 251004025361783061L;

    @TableField(value = "asin")
    private String asin;

    @TableField(value = "sku")
    private String sku;

    @TableField(value = "amazonAuthId")
    private String amazonAuthId;

    @TableField(value = "marketplaceid")
    private String marketplaceid;

    @TableField(value = "lastupdateTime")
    private Date lastupdateTime;

    @TableField(value = "isread")
    private Boolean isread;

    @TableField(value = "flownumber")
    private Integer flownumber;


}