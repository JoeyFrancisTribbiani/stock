package com.yy.stock.adaptor.amazon.api.pojo.dto;


import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProductPresaleListDTO extends BasePageQuery {
    String groupid;
    String shopid;
    String sku;
    String msku;
    String marketplaceid;
    String asin;
    String owner;
    Boolean needplan;
    Date fromDate;
    Date toDate;
}
