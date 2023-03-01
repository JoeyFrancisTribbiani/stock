package com.yy.stock.adaptor.amazon.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "t_amz_order_buyer_ship_address")
public class AmzOrderBuyerShipAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "amazon_order_id")
    private String amazonOrderId;

    @Column(name = "marketplaceId")
    private String marketplaceId;

    @Column(name = "amazonAuthId")
    private String amazonAuthId;

    @Column(name = "name")
    private String name;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "city")
    private String city;

    /**
     * 区县
     */
    @Column(name = "county")
    private String county;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    @Column(name = "postalcode")
    private String postalcode;

    @Column(name = "countrycode")
    private String countrycode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "opttime")
    private Date opttime;

}
