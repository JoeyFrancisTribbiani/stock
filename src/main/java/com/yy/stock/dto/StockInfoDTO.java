package com.yy.stock.dto;

import com.yy.stock.entity.Status;
import lombok.Data;

@Data
public class StockInfoDTO {
    OrderItemAdaptorInfoDTO orderItemAdaptorInfo;
    Status status;
}
