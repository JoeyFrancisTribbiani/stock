package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_in_aftersale")
@ApiModel(value = "ProductInAftersale对象", description = "")
public class ProductInAftersale extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private BigInteger groupid;

    private BigInteger amazonauthid;

    private String marketplaceid;

    private String sku;

    private Date date;

    private Integer quantity;


}
