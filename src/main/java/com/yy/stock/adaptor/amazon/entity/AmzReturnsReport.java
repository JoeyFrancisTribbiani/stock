package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "t_amz_returns_report")
public class AmzReturnsReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AmzReturnsReportUPK amzReturnsReportUPK;

    @Column(name = "marketplaceid")
    private String marketplaceid;

    @Column(name = "asin")
    private String asin;

    @Column(name = "fnsku")
    private String fnsku;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "fulfillment_center_id")
    private String fulfillmentCenterId;

    @Column(name = "detailed_disposition")
    private String detailedDisposition;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private String status;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    @Column(name = "customer_comments")
    private String customerComments;

}
