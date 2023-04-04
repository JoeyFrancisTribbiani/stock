package com.yy.stock.vo;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class OrderItemStatusQueryVO implements Serializable {
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
