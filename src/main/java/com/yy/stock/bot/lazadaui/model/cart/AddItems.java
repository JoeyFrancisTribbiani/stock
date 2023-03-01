/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.lazadaui.model.cart;

/**
 * Auto-generated: 2023-02-16 22:28:57
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class AddItems {

    private int quantity;
    private ExtendInfo extendInfo;
    private long itemId;
    private Attributes attributes;
    private long skuId;
    private long cartItemId;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ExtendInfo getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(ExtendInfo extendInfo) {
        this.extendInfo = extendInfo;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }

}