package com.yy.stock.adaptor.amazon.api.pojo.dto;

import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AmzFinDataDTO extends BasePageQuery {

    String itemid;
    String marketplaceid;
    String groupid;
    String fromDate;
    String endDate;
    String sku;
    String osku;

}
