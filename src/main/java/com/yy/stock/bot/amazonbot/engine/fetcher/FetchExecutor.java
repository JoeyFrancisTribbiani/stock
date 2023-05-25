package com.yy.stock.bot.amazonbot.engine.fetcher;

import com.yy.stock.bot.engine.core.BotStatus;
import lombok.Getter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
@Getter
public class FetchExecutor {
    private AmazonFetcherEngine fetcherEngine;

    public void init(AmazonFetcherEngine fetcherEngine) {
        this.fetcherEngine = fetcherEngine;
    }

    @Async("asyncServiceExecutor")
    public void fetchAsync(String url) throws InterruptedException, MessagingException, IOException {
        fetcherEngine.fetch(url);
        this.fetcherEngine.getCoreEngine().setBotStatus(BotStatus.idle);
    }
}
