package com.yy.stock.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class OrderItemStatusVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private String id;

    @NotNull(message = "orderStatusId can not null")
    private Long orderStatusId;

    @NotNull(message = "itemId can not null")
    private String itemId;

    @NotNull(message = "status can not null")
    private Integer status;

    private String shipment;

    private Date stockTime;

    @NotNull(message = "quantity can not null")
    private Integer quantity;

    @NotNull(message = "totalPrice can not null")
    private Float totalPrice;

}
