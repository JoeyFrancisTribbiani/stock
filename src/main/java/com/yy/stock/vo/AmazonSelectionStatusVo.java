package com.yy.stock.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import com.yy.stock.entity.AmazonSelection;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonSelectionStatusVo extends AmazonSelection {
    private static final long serialVersionUID = 1L;

    private BigInteger amazonAuthId;

    private Boolean hasFollowSell;

    private Boolean followSellSwitch;

}
