package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProductPriceDTO extends BasePageQuery {

    private String groupid;

    private String shopid;

    private String marketplaceid;

    private String searchtype;

    private String search;

    private String sku;

    private String status;

    private String startDate;

    private String endDate;

    private String operator;

}
