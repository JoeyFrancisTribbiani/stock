package com.yy.stock.adaptor.amazon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OrdersFulfilledShipmentsReportID implements Serializable {

    private String amazonOrderId;
    private String shipmentItemId;
    private String amazonOrderItemId;
}
