package com.yy.stock.adaptor.amazon.api.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AmzProductPageviews汇总结果", description = "流量报表")
public class AmzProductPageviewsConditionVo {

    private BigInteger amazonAuthid;
    private String marketplaceid;
    private Date byday;

    private Date opttime;
}
