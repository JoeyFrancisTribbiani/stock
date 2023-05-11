package com.yy.stock.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import com.yy.stock.bot.amazonbot.model.GatherEntrance;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonSelectionQueryVO extends BasePageQuery {
    private static final long serialVersionUID = 1L;

    private BigInteger amazonAuthId;
    private String marketplaceId;
    private String categoryId;
    private String asin;
    private String sku;
    private GatherEntrance gatherEntrance;
    private String searchKey;
    private boolean confirmSell;
    private boolean hasFollowSell;
    private boolean canFollowSell;
    private boolean followSellSwitch;

}
