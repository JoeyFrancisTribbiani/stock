package com.yy.stock.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String marketplaceId;

    private String amazonOrderId;

    private Long supplierId;

    private Long buyerId;

    private Date stockTime;

    private Integer status;

    private String shipment;

}
