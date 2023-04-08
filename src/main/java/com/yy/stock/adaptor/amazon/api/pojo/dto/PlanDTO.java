package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class PlanDTO extends BasePageQuery {
    String groupid;
    String warehouseid;
    String planid;
    String plantype;
    String marketplaceid;
    String shortdays;
    String search;
    Boolean selected;
    String status;
    String status2;
    String owner;
    String categoryid;
    String shopid;
    String orderparam;
    String sortparam;
    String ordercountry;
    String msku;
    Integer issfg;
    String ischeck;
    String skuarray;
    String proname;
    String small;
    String remark;
    String currentRank;
    String hasAddFee;
    String defoutwarehouseid;
    String samesearch;
    String searchtype;
    List<String> mskulist;
    List<String> skulist;
    List<String> pidlist;
    List<String> tags;
}
