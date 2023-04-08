package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProductListQuery extends BasePageQuery {
    String search;
    String searchtype;
    String marketplace;
    String disable;
    String changeRate;
    String color;
    String isfba;
    String groupid;
    String ownerid;
    String isparent;
    String parentasin;
    String name;
    String remark;
    String category;
    String isbadreview;
    String salestatus;
    String sku;
    String paralist;
    List<String> taglist;
}
