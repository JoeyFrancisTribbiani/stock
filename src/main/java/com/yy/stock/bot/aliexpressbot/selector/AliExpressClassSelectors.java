package com.yy.stock.bot.aliexpressbot.selector;

import com.yy.stock.bot.selector.BaseClassSelector;
import com.yy.stock.common.util.YamlSourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource(value = "classpath:configs/aliexpress.yml", factory = YamlSourceFactory.class)    // 指定自定义配置文件位置和名称
@ConfigurationProperties(prefix = "aliclass")        // 指定配置文件注入属性前缀
public class AliExpressClassSelectors extends BaseClassSelector {
//    private String loginTopButton;
//    private String accountInput;
//    private String accountTopButtonSpan;
//    private String passwordInput;
//    private String loginButton;
//    private String accountVerifyButton;
//    private String verifyEmailSendButton;
//    private String verifyEmailCodeInput;
//    private String verifySubmitButton;
//    private String selectAllCartItemsDiv;
//    private String selectAllCartItemsButton;
//    private String deleteAllCartItemsButton;
//    private String deleteCartItemsConfirmButton;
//    private String cartEmptyTipsDiv;
//    private String subtotalDiv;
//    private String shippingFeeDiv;
//    private String totalPriceDiv;
//    private String checkoutButton;
}
