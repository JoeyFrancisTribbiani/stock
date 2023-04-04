package com.yy.stock.adaptor.amazon.api.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AmazonGroup {
    private static final long serialVersionUID = 4632144329348858750L;
    @ApiModelProperty(value = "匹配的利润计算方案ID")
    protected String profitcfgid;
    @ApiModelProperty(value = "店铺名称")
    private String name;
    @ApiModelProperty(value = "公司ID【系统填写】")
    private String shopid;
    @ApiModelProperty(value = "操作人ID【系统填写】")
    private String operator;

    @ApiModelProperty(value = "操作时间【系统填写】")
    private Date opttime;

    @ApiModelProperty(value = "创建人ID【系统填写】")
    private String creator;

    @ApiModelProperty(value = "创建时间【系统填写】")
    private Date createtime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isdelete;
}