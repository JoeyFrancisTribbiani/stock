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
public class AmzOrderItemUPK implements Serializable {

    @Column(name = "`amazon_order_id`", nullable = false)
    private String amazonOrderId;

    @Column(name = "`orderItemId`", nullable = false)
    private String orderItemId;
}
