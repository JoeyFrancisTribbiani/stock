package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wimoor team
 * @since 2022-11-28
 */
import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_amz_product_sales_plan")
@ApiModel(value = "AmzProductSalesPlan对象", description = "")
public class AmzProductSalesPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String sku;

    private String msku;

    private BigInteger shopid;

    private String marketplaceid;

    private String groupid;

    private BigInteger amazonauthid;

    private Integer shipday;

    private Integer salesday;

    private Integer deliveryCycle;

    private Integer needship;

    private Integer shipMinCycleSale;

    private Integer needshipfba;

    private Integer avgsales;

    private Integer needpurchase;

    private Date opttime;

    private LocalDate shortTime;

    public void setSalesday(Integer salesday) {
        // TODO Auto-generated method stub
        this.salesday = salesday;
    }


}
