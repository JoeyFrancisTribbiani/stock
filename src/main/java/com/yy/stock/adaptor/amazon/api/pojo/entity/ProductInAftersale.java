package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wimoor team
 * @since 2022-11-28
 */
@Data
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
