package com.yy.stock.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import jakarta.persistence.*;

import java.math.BigInteger;

@Data
@Entity
@Table(name = "amazon_selection_has_follow")
public class AmazonSelectionHasFollow {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger id;

    @Column(name = "amazon_auth_id", nullable = false)
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger amazonAuthId;

    @JoinColumn(name = "amazonSelectionId")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger amazonSelectionId;

    @Column(name = "has_follow_sell")
    private Boolean hasFollowSell;

    @Column(name = "sku")
    private String sku;

    @Column(name = "follow_sell_switch")
    private Boolean followSellSwitch;
    @Column(name = "win_purchase_button")
    private Boolean winPurchaseButton;
    @Column(name = "set_price")
    private String setPrice;

}
