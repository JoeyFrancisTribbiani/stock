package com.yy.stock.adaptor.amazon.api.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("t_product_category")
public class ProductCategory {

    @TableId(value = "CategoryId")
    private String categoryid;

    @TableField(value = "pid")
    private String pid;

    @TableField(value = "Name")
    private String name;

    @TableField(value = "parentId")
    private String parentid;

}