package com.yy.stock.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yy.stock.entity.AmazonSelection;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonSelectionStatusVo extends AmazonSelection {
    private static final long serialVersionUID = 1L;


    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger amazonAuthId;

    private Boolean hasFollowSell;
    private Boolean followSellSwitch;
    private Boolean winPurchaseButton;
    private String setPrice;

}
