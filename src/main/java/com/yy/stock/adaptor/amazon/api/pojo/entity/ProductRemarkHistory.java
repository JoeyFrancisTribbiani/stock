package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_remark_his")
@ApiModel(value = "AmzProductRemarkHis对象", description = "备注操作记录")
public class ProductRemarkHistory {

    @TableField("pid")
    private String pid;

    @TableField("ftype")
    private String ftype;

    @TableField("opttime")
    private Date opttime;

    private String operator;

    private String remark;

}
