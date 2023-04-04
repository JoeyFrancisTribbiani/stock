package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_amz_orders_address")
public class AmzOrdersAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "amazon_order_id", nullable = false)
    private String amazonOrderId;

    @Column(name = "amazonAuthId")
    private BigInteger amazonAuthId;

    @Column(name = "marketplaceId")
    private String marketplaceId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "address_line3")
    private String addressLine3;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "city")
    private String city;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "county")
    private String county;

    @Column(name = "district")
    private String district;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "state_or_region")
    private String stateOrRegion;

    @Column(name = "opttime")
    private Date opttime;

}
