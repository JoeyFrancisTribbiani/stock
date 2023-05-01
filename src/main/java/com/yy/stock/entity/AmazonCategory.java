package com.yy.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "amazon_category")
public class AmazonCategory {

    @Id
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "traversal_status", nullable = false)
    private String traversalStatus;

    @Column(name = "last_traversal_time")
    private Date lastTraversalTime;

}
