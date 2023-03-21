package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * purchase_order_number
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "`t_amz_order_item`")
public class AmzOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "`id`", nullable = false, columnDefinition = "bigint")
    private BigInteger id;

    //    @Id
//    @Column(name = "`amazon_order_id`", nullable = false)
//    private String amazonOrderId;
    @EmbeddedId
    private AmzOrderItemUPK amzOrderItemUPK;


    @Column(name = "`purchase_date`", nullable = false)
    private LocalDateTime purchaseDate;

    @Column(name = "`last_updated_date`")
    private LocalDateTime lastUpdatedDate;

//    @Id
//    @Column(name = "`orderItemId`", nullable = false)
//    private String orderItemId;

    @Column(name = "`asin`", columnDefinition = "char")
    private String asin;

    @Column(name = "`sku`", nullable = false, columnDefinition = "char")
    private String sku;

    @Column(name = "`title`")
    private String title;

    @Column(name = "`QuantityOrdered`")
    private Integer quantityOrdered;

    @Column(name = "`QuantityShipped`")
    private Integer quantityShipped;

    @Column(name = "`currency`", columnDefinition = "char")
    private String currency;

    @Column(name = "`item_price`")
    private BigDecimal itemPrice;

    @Column(name = "`item_tax`")
    private BigDecimal itemTax;

    @Column(name = "`shipping_price`")
    private BigDecimal shippingPrice;

    @Column(name = "`shipping_tax`")
    private BigDecimal shippingTax;

    @Column(name = "`gift_wrap_price`")
    private BigDecimal giftWrapPrice;

    @Column(name = "`gift_wrap_tax`")
    private BigDecimal giftWrapTax;

    @Column(name = "`item_promotion_discount`")
    private BigDecimal itemPromotionDiscount;

    @Column(name = "`ship_promotion_discount`")
    private BigDecimal shipPromotionDiscount;

    @Column(name = "`promotion_ids`")
    private String promotionIds;

    @Column(name = "`CODFee`")
    private BigDecimal CODFee;

    @Column(name = "`CODFeeDiscount`")
    private BigDecimal CODFeeDiscount;

    @Column(name = "`GiftMessageText`")
    private String giftMessageText;

    @Column(name = "`GiftWrapLevel`", columnDefinition = "char")
    private String giftWrapLevel;

    @Column(name = "`ConditionId`", columnDefinition = "char")
    private String conditionId;

    @Column(name = "`ConditionSubtypeId`", columnDefinition = "char")
    private String conditionSubtypeId;

    @Column(name = "`ConditionNote`")
    private String conditionNote;

    @Column(name = "`ScheduledDeliveryStartDate`")
    private LocalDateTime scheduledDeliveryStartDate;

    @Column(name = "`ScheduledDeliveryEndDate`")
    private LocalDateTime scheduledDeliveryEndDate;

    @Column(name = "`amazonAuthId`", nullable = false, columnDefinition = "bigint")
    private BigInteger amazonAuthId;

    @Column(name = "`marketplaceId`", columnDefinition = "char")
    private String marketplaceId;

}
