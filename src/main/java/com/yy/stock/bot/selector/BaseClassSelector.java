package com.yy.stock.bot.selector;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)


public class BaseClassSelector {
    // 地址相关
    public String regionCheckListButton;
    public String regionArrayLis;
    public String regionSaveButton;
    public String homePageAdCloseButton;

}
