package com.yy.stock.entity;

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
    private BigInteger id;

    @Column(name = "amazon_auth_id", nullable = false)
    private BigInteger amazonAuthId;

    @JoinColumn(name = "amazonSelectionId")
    private BigInteger amazonSelectionId;

    @Column(name = "has_follow_sell")
    private Boolean hasFollowSell;

    @Column(name = "sku")
    private String sku;

    @Column(name = "follow_sell_switch")
    private Boolean followSellSwitch;

}
