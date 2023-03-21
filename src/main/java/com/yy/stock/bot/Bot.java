package com.yy.stock.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.base.Product;
import com.yy.stock.bot.base.ShipmentInfo;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.entity.BuyerAccount;

import java.io.IOException;

public interface Bot {
    String getBotName();

    public boolean doStock(StockRequest requestDTO) throws InterruptedException, JsonProcessingException;

    public ShipmentInfo trackOrder(String orderId);

    public boolean returnOrder(Product product);

    public void setBuyerAccount(BuyerAccount buyerAccount);

    public String getProductHtmlSource(String url) throws IOException, InterruptedException;


    String getSkuProperties(String html);

    void quit();
}
