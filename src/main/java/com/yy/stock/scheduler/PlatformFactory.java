package com.yy.stock.scheduler;

import com.yy.stock.common.util.SpringUtil;
import com.yy.stock.entity.Platform;
import com.yy.stock.service.PlatformService;
import org.springframework.stereotype.Component;

@Component
public class PlatformFactory {

    public static Platform getPlatformByUrl(String url, String countryCode) {
        PlatformService platformService = SpringUtil.getBean("platformService");
        if (url.contains("aliexpress")) {
            return platformService.getByNameAndCountryCode("Aliexpress.com", countryCode);
        }
        return null;
    }
}
