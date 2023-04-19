package com.yy.stock.scheduler;

import com.yy.stock.common.util.MySpringUtil;
import com.yy.stock.entity.Platform;
import com.yy.stock.service.PlatformService;
import org.springframework.stereotype.Component;

@Component
public class PlatformFactory {

    public static Platform getPlatformByUrl(String url, String countryCode) {
        PlatformService platformService = MySpringUtil.getBean("platformService");
        if (url.contains("aliexpress")) {
            return platformService.getByCodeAndCountryCode("aliexpress", countryCode);
        }
        return null;
    }
}
