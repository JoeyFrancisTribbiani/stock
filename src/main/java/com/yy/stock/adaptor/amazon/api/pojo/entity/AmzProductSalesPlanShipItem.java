package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wimoor team
 * @since 2022-12-10
 */
import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_amz_product_sales_plan_ship_item")
@ApiModel(value = "AmzProductSalesPlanShipItem对象", description = "")
public class AmzProductSalesPlanShipItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String sku;

    private String msku;

    private BigInteger shopid;

    private String marketplaceid;

    private BigInteger groupid;

    private BigInteger amazonauthid;

    private BigInteger warehouseid;

    private BigInteger overseaid;

    private BigInteger transtype;

    private String batchnumber;

    private Integer amount;

    private Integer aftersalesday;

    private LocalDateTime opttime;

    private BigInteger operator;

    @TableField(exist = false)
    private Integer subnum;
}
