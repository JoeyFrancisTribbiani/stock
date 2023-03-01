package com.yy.stock.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private Long orderItemId;

    private Long supplierId;

    private LocalDateTime stockTime;

    private Integer status;

    private String shipment;

    private String amazonOrderId;

    private String marketplaceId;

    private String amazonauthId;

}
