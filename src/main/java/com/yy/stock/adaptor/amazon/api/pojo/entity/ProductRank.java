package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_rank")
public class ProductRank extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime byday;

    @TableField("categoryId")
    private String categoryid;

    private Integer rank;

    private String productId;

    @TableField("isMain")
    private Boolean ismain;

    @TableField("isNewest")
    private Boolean isnewest;

}
