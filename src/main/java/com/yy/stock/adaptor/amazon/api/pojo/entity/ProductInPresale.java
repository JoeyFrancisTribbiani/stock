package com.yy.stock.adaptor.amazon.api.pojo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yy.stock.adaptor.amazon.api.pojo.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("t_product_in_presale")
public class ProductInPresale extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8401333489880986919L;

	@TableField(value= "sku")
	private String sku;

    @TableField(value= "marketplaceid")
    private String marketplaceid;

    @TableField(value= "groupid")
    private String groupid;

    @TableField(value= "amazonauthid")
    private String amazonauthid;
    
	@TableField(value= "date")
    private Date date;

	@TableField(value= "quantity")
    private Integer quantity;

	@TableField(value= "operator")
    private String operator;

	@TableField(value= "opttime")
    private Date opttime;
	
}