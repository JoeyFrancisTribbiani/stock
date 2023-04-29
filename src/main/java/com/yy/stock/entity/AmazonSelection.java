package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "amazon_selection")
public class AmazonSelection {

    @Id
    @Column(name = "asin", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String asin;

    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;


    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "search_key")
    private String searchKey;
}
