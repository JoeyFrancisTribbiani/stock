package com.yy.stock.adaptor.amazon.api.pojo.vo;

import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.Platform;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.entity.Supplier;
import lombok.Data;

@Data
public class StockOrderStatusListVo extends AmazonOrdersVo {
    Supplier supplier;
    Platform platform;
    StockStatus stockStatus;
    BuyerAccount buyerAccount;
}
