package com.yy.stock.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yy.stock.bot.amazonbot.model.GatherEntrance;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "amazon_selection")
public class AmazonSelection {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger id;

    @Column(name = "asin", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String asin;

    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;

    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "gather_entrance")
    @Enumerated(EnumType.STRING)
    private GatherEntrance gatherEntrance;
    @Column(name = "sku")
    private String sku;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "price", nullable = false)
    private String price;
    @Column(name = "search_key")
    private String searchKey;
    @Column(name = "confirm_sell")
    private Boolean confirmSell;
    @Column(name = "can_follow_sell")
    private Boolean canFollowSell;
    @Column(name = "confirm_supplier")
    private Boolean confirmSupplier;
    @Column(name = "has_supplier")
    private Boolean hasSupplier;
    @Column(name = "pic_url")
    private String picUrl;
    @Column(name = "seller_id")
    private String sellerId;
    @Column(name = "parent_url")
    private String parentUrl;
    @Column(name = "has_favorite")
    private Boolean hasFavorite;
}
