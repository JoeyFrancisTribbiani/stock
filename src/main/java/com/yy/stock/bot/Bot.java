package com.yy.stock.bot;

import com.yy.stock.bot.base.LoginRequest;
import com.yy.stock.bot.base.Product;
import com.yy.stock.bot.base.ShipmentInfo;

import java.util.List;

public interface Bot {
    public boolean login(LoginRequest loginRequest);

    public boolean placeOrder(List<Product> productList);

    public ShipmentInfo trackOrder(String orderId);

    public boolean returnOrder(Product product);
}
