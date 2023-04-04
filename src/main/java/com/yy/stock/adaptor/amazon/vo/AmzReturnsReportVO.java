package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AmzReturnsReportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "sku can not null")
    private String sku;

    @NotNull(message = "returnDate can not null")
    private Date returnDate;

    @NotNull(message = "orderId can not null")
    private String orderId;

    @NotNull(message = "sellerid can not null")
    private String sellerid;

    private String marketplaceid;

    private String asin;

    private String fnsku;

    private Integer quantity;

    private String fulfillmentCenterId;

    private String detailedDisposition;

    private String reason;

    private String status;

    private String licensePlateNumber;

    private String customerComments;

}
