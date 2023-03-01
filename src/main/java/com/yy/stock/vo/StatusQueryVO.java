package com.yy.stock.vo;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StatusQueryVO implements Serializable {
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
