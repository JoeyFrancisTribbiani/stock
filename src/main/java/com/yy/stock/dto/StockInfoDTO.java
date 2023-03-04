package com.yy.stock.dto;

import com.yy.stock.entity.StockStatus;
import lombok.Data;

@Data
public class StockInfoDTO {
    OrderItemAdaptorInfoDTO orderItemAdaptorInfo;
    StockStatus stockStatus;
}
