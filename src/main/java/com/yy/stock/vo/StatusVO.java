package com.yy.stock.vo;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;


import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class StatusVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private String id;

    @NotNull(message = "marketplaceId can not null")
    private String marketplaceId;

    @NotNull(message = "amazonOrderId can not null")
    private String amazonOrderId;

    @NotNull(message = "supplierId can not null")
    private Long supplierId;

    @NotNull(message = "buyerId can not null")
    private Long buyerId;

    private Date stockTime;

    @NotNull(message = "status can not null")
    private Integer status;

    private String shipment;

}
