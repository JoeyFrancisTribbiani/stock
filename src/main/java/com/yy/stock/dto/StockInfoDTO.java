package com.yy.stock.dto;

import com.yy.stock.entity.StockStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockInfoDTO {
    OrderItemAdaptorInfoDTO orderItemAdaptorInfoDTO;
    StockStatus stockStatus;
}
