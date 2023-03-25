package com.yy.stock.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.base.Product;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;

import java.io.IOException;

public interface Bot {
    String getBotName();

    boolean doStock(StockRequest requestDTO) throws InterruptedException, JsonProcessingException;

    void doTrack(TrackRequest trackRequest) throws JsonProcessingException, InterruptedException;

    boolean returnOrder(Product product);

    void setBuyerAccount(BuyerAccount buyerAccount);

    String getProductHtmlSource(String url) throws IOException, InterruptedException;

    String getSkuProperties(String html);

    void quitDriver();
}
