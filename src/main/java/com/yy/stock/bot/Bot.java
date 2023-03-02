package com.yy.stock.bot;

import com.yy.stock.bot.base.Product;
import com.yy.stock.bot.base.ShipmentInfo;
import com.yy.stock.dto.StockRequest;

public interface Bot {
    public boolean doStock(StockRequest requestDTO);

    public ShipmentInfo trackOrder(String orderId);

    public boolean returnOrder(Product product);
}
