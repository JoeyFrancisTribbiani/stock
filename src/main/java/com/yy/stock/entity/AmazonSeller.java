package com.yy.stock.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "amazon_seller")
public class AmazonSeller {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "is_follow_seller")
    private Boolean followSeller;

    @Column(name = "name")
    private String name;

    @Column(name = "home_page_url")
    private String homePageUrl;

    @Column(name = "store_front_url")
    private String storeFrontUrl;

    @Column(name = "last_product_num")
    private Integer lastProductNum;

    @Column(name = "last_fetch_time")
    private Date lastFetchTime;

}
