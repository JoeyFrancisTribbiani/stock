package com.yy.stock.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.base.Product;
import com.yy.stock.bot.base.ShipmentInfo;
import com.yy.stock.dto.StockRequest;

public interface Bot {
    public boolean doStock(StockRequest requestDTO) throws InterruptedException, JsonProcessingException;

    public ShipmentInfo trackOrder(String orderId);

    public boolean returnOrder(Product product);
}
