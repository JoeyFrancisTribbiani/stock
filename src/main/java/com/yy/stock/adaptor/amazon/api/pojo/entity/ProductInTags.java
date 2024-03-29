package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_in_tags")
public class ProductInTags implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5469505744517586722L;

    @TableField(value = "pid")
    String pid;

    @TableField(value = "tagid")
    String tagid;

    @TableField(value = "operator")
    String operator;

    @TableField(value = "opttime")
    Date opttime;
}
