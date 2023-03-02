package com.yy.stock.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderItemStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private Long orderStatusId;

    private String itemId;

    private Integer status;

    private String shipment;

    private Date stockTime;

    private Integer quantity;

    private Float totalPrice;

}
