package com.yy.stock.dto;

import com.yy.stock.adaptor.amazon.entity.AmzOrdersAddress;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.Platform;
import com.yy.stock.entity.Status;
import com.yy.stock.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockRequest {
    private Status status;
    private OrderItemAdaptorInfoDTO orderInfo;
    private Platform platform;
    private Supplier supplier;
    private BuyerAccount buyerAccount;
    private AmzOrdersAddress address;
}
