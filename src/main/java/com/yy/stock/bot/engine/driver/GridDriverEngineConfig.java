package com.yy.stock.bot.engine.driver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class GridDriverEngineConfig {
    @Value("${bot.gridhub.registerUrl}")
    private String registerUrl;
}
