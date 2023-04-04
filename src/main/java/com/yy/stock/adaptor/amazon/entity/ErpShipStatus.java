package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "t_erp_ship_status")
public class ErpShipStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "name")
    private String name;

}
