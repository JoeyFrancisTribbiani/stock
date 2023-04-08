package com.yy.stock.adaptor.amazon.api.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AmazonGroup {
    private static final long serialVersionUID = 4632144329348858750L;
    protected String profitcfgid;
    private String name;
    private String shopid;
    private String operator;

    private Date opttime;

    private String creator;

    private Date createtime;

    private Boolean isdelete;
}