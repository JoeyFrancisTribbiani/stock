package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * stock record from supplier
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "order_item_status")
public class OrderItemStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "order_status_id", nullable = false)
    private Long orderStatusId;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "shipment")
    private String shipment;

    @Column(name = "stock_time")
    private Date stockTime;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

}
