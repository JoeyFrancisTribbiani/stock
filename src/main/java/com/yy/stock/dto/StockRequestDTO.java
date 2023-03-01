package com.yy.stock.dto;

import com.yy.stock.adaptor.amazon.dto.AmzOrdersAddressDTO;
import com.yy.stock.adaptor.amazon.dto.OrdersReportDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockRequestDTO {
    private OrdersReportDTO orderInfo;
    private SupplierDTO supplier;
    private BuyerAccountDTO buyerAccount;
    private AmzOrdersAddressDTO address;
}
