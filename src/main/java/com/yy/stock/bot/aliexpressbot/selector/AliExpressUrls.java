package com.yy.stock.bot.aliexpressbot.selector;

import com.yy.stock.bot.selector.BaseUrls;
import com.yy.stock.common.util.YamlSourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@PropertySource(value = "classpath:configs/aliexpress.yml", factory = YamlSourceFactory.class)    // 指定自定义配置文件位置和名称
@ConfigurationProperties(prefix = "aliurls")        // 指定配置文件注入属性前缀
@Component("aliExpressUrls")
public class AliExpressUrls extends BaseUrls {
//    public String homePage;
//    public String loginPage;
//    public String addToCartApi;
//    public String cartPage;
//    public String placeOrderPage;
//    public String listAddressApi;
//    public String getByPostCodeApi;
//    public String validatePhoneApi;
//    public String createAddressApi;
//    public String deleteAddressApi;
//    public String logisticViewPage;
//    public String cartCountApi;
}
