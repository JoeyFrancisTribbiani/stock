package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class AmazonsellerMarketUPK implements Serializable {

    @Column(name = "`sellerid`", nullable = false)
    private String sellerid;

    @Column(name = "`marketplace_id`", nullable = false)
    private String marketplaceId;
}
