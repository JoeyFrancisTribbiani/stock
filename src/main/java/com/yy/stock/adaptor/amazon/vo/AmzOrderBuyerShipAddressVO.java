package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class AmzOrderBuyerShipAddressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
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
