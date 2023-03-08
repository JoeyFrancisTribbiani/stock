package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

/**
 * sync orders
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "sync_order")
public class SyncOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 亚马逊店铺ID
     */
    @Column(name = "amazon_auth_id", nullable = false)
    private BigInteger amazonAuthId;

    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;
    /**
     * 亚马逊sku
     */
    @Column(name = "sync_host", nullable = false)
    private String syncHost;

    /**
     * 商品名称
     */
    @Column(name = "sync_port", nullable = false)
    private Integer syncPort;

    /**
     * 是否启用货源
     */
    @Column(name = "sync_switch")
    private boolean syncSwitch;

    /**
     * 商品asin
     */
    @Column(name = "report_name_prefix")
    private String reportNamePrefix;

    /**
     * 货源平台
     */
    @Column(name = "last_update_timestamp", nullable = false)
    private BigInteger lastUpdateTimestamp;

    /**
     * 货源链接
     */
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

}
