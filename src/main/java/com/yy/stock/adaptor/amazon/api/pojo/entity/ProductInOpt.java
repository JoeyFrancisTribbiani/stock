package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_in_opt")
public class ProductInOpt implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private BigInteger pid;

    private String remark;

    private String priceremark;

    private BigDecimal buyprice;

    private BigDecimal businessprice;

    private String businesstype;

    private String businesslist;

    private Boolean disable;

    private Integer presales;

    private LocalDateTime lastupdate;

    private String remarkAnalysis;

    private String msku;

    private String fnsku;

    private Integer reviewDailyRefresh;

    private BigInteger profitid;

    private Integer status;

    private String owner;

    private BigInteger operator;


}
