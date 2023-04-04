package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Date;

@Builder

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class AmzReturnsReportUPK {
    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "return_date", nullable = false)
    private Date returnDate;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "sellerid", nullable = false)
    private String sellerid;
}
