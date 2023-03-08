package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.entity.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ShipPlanDTO对象", description = "发货计划")
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
