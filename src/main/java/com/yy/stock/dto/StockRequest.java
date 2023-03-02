package com.yy.stock.dto;

import com.yy.stock.adaptor.amazon.dto.AmzOrdersAddressDTO;
import com.yy.stock.adaptor.amazon.entity.OrdersReport;
import com.yy.stock.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockRequest {
    private Status status;
    private OrdersReport orderInfo;
    private PlatformDTO platform;
    private SupplierDTO supplier;
    private BuyerAccountDTO buyerAccount;
    private AmzOrdersAddressDTO address;
}
