package com.yy.stock.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class StatusVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private String id;

    @NotNull(message = "orderItemId can not null")
    private Long orderItemId;

    @NotNull(message = "supplierId can not null")
    private Long supplierId;

    @NotNull(message = "stockTime can not null")
    private LocalDateTime stockTime;

    @NotNull(message = "status can not null")
    private Integer status;

    private String shipment;

    @NotNull(message = "amazonOrderId can not null")
    private String amazonOrderId;

    private String marketplaceId;

    private String amazonauthId;

}
