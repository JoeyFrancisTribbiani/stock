package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProductListDTO extends BasePageQuery {

    String warehouseid;

    String ftype;

    String marketplaceid;

    String shopid;

    String groupid;

    String amazonauthid;

    String search;


}
