package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("t_product_in_order")
public class ProductInOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private BigInteger pid;

    private Integer avgsales;

    private Integer oldavgsales;

    private Integer daynum;

    private Integer salesWeek;

    private BigDecimal priceWeek;

    private Integer salesMonth;

    private Integer orderWeek;

    private Integer orderMonth;

    @TableField("changeRate")
    private BigDecimal changeRate;

    private LocalDateTime lastupdate;

    private Integer salesSeven;

    private Integer salesFifteen;

    private Integer rank;


}
