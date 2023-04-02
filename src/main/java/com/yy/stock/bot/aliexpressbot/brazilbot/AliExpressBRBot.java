package com.yy.stock.bot.aliexpressbot.brazilbot;

import com.yy.stock.bot.aliexpressbot.AliExpressBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
@Scope("prototype")
@Slf4j
public class AliExpressBRBot extends AliExpressBot {
    public AliExpressBRBot() throws MalformedURLException {
        super();
        log.info("构造AliExpressBrazilBot Bean!");
    }

}
