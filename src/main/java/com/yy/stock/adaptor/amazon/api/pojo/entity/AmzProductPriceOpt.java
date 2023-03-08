package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_amz_pdt_price_opt")
@ApiModel(value = "AmzProductPriceOpt对象", description = "改价操作记录")
public class AmzProductPriceOpt {

    @TableField("feed_submission_id")
    private String feedSubmissionId;

    @TableField("pid")
    private String pid;

    private BigDecimal standardprice;

    private BigDecimal saleprice;

    private BigDecimal businessprice;

    private BigDecimal oldstandardprice;

    private String businesstype;

    private String businesslist;

    private Date starttime;

    private Date endtime;

    private Date opttime;

    private String ftype;

    private String operator;
}
