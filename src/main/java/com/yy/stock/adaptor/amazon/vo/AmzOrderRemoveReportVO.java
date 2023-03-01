package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class AmzOrderRemoveReportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "orderId can not null")
    private String orderId;

    @NotNull(message = "amazonAuthId can not null")
    private String amazonAuthId;

    private Date purchaseDate;

    private Date lastUpdatedDate;

    private String orderType;

    private String orderStatus;

    @NotNull(message = "sku can not null")
    private String sku;

    private String fnsku;

    private String disposition;

    private Integer requestedQuantity;

    private Integer cancelledQuantity;

    private Integer disposedQuantity;

    private Integer shippedQuantity;

    private Integer inProcessQuantity;

    private BigDecimal removalFee;

    private String currency;

}
