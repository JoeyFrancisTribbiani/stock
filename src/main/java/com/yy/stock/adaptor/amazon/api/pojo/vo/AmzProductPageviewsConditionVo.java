package com.yy.stock.adaptor.amazon.api.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.Date;

/**
 * <p>
 * 流量报表
 * </p>
 *
 * @author wimoor team
 * @since 2022-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AmzProductPageviews汇总结果", description = "流量报表")
public class AmzProductPageviewsConditionVo {

    private BigInteger amazonAuthid;
    private String marketplaceid;
    private Date byday;

    private Date opttime;
}
