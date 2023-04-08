package com.yy.stock.bot.selector;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component("baseUrls")
@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public class BaseUrls {
    public String homePage;
    public String testLogin;
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
    public String orderListPage;
    public String logisticDetailApi;
    public String logisticViewPage;
    public String cartCountApi;
    public String cainiaoGlobalTrackApi;
}
