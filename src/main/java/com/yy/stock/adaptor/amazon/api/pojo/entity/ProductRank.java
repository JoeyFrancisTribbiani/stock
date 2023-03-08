package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yy.stock.adaptor.amazon.api.pojo.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wimoor team
 * @since 2022-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_rank")
@ApiModel(value="ProductRank对象", description="")
public class ProductRank extends BaseEntity implements Serializable {

    private static final long serialVersionUID=1L;

    private LocalDateTime  byday;

    @TableField("categoryId")
    private String categoryid;

    private Integer rank;

    private String productId;

    @TableField("isMain")
    private Boolean ismain;

    @TableField("isNewest")
    private Boolean isnewest;

}
