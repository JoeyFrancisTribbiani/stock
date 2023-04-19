package com.yy.stock.bot.lazadabot.selector;

import com.yy.stock.common.util.YamlSourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@PropertySource(value = "classpath:configs/lazada.yml", factory = YamlSourceFactory.class)    // 指定自定义配置文件位置和名称
//@EnableConfigurationProperties(LazadaUrls.class)        // 开启对应配置类的属性注入功能
@ConfigurationProperties(prefix = "urls")        // 指定配置文件注入属性前缀
@Component
public class LazadaUrls {
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
    public String logisticViewPage;
    public String cartCountApi;
}
