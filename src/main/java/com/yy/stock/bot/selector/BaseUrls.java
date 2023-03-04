package com.yy.stock.bot.selector;

import lombok.Data;

//@Configuration        // 自定义配置类
@Data
public class BaseUrls {
    public String homePage;
    public String loginPage;
    public String addToCartApi;
    public String cartPage;
    public String placeOrderPage;
    public String listAddressApi;
    public String getByPostCodeApi;
    public String validatePhoneApi;
    public String createAddressApi;
    public String deleteAddressApi;
    public String addressListPage;
    public String confirmPaymentPage;
    public String logisticViewPage;
    public String cartCountApi;
}
