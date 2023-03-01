package com.yy.stock.adaptor.amazon.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AmzOrderBuyerShipAddressDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String amazonOrderId;

    private String marketplaceId;

    private String amazonAuthId;

    private String name;

    private String address1;

    private String address2;

    private String address3;

    private String city;


    /**
     * 区县
     */
    private String county;

    private String district;

    private String state;

    private String postalcode;

    private String countrycode;

    private String phone;

    private String email;

    private Date opttime;

}
