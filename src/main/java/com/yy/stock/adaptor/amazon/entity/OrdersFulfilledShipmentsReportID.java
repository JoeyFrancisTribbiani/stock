package com.yy.stock.adaptor.amazon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OrdersFulfilledShipmentsReportID implements Serializable {

    private String amazonOrderId;
    private String shipmentItemId;
    private String amazonOrderItemId;
}
