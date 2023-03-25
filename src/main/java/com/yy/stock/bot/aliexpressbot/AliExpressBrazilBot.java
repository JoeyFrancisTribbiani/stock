package com.yy.stock.bot.aliexpressbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public class AliExpressBrazilBot extends AliExpressBot {
    public AliExpressBrazilBot() {
        super();
        log.info("构造AliExpressBrazilBot Bean!");
    }

}
