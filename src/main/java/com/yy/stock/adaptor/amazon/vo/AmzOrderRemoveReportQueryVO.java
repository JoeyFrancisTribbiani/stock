package com.yy.stock.adaptor.amazon.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AmzOrderRemoveReportQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;

    private String amazonAuthId;

    private Date purchaseDate;

    private Date lastUpdatedDate;

    private String orderType;

    private String orderStatus;

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
