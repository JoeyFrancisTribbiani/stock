package com.yy.stock.adaptor.amazon.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AmzReturnsReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sku;

    private Date returnDate;

    private String orderId;

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
