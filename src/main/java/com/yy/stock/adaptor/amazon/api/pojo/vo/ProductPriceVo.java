package com.yy.stock.adaptor.amazon.api.pojo.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductPriceVo {
    String gname;
    String mname;
    String pname;
    String sku;
    String image;
    String standardprice;
    String oldstandardprice;
    String feedid;
    String status;
    String statusText;
    String operator;
    Date opttime;
    Date successdate;
    String fnsku;
    String ftype;

}
