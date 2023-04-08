package com.yy.stock.bot.engine.fetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.engine.PluggableEngine;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.loginer.LoginEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.config.GlobalVariables;

import javax.mail.MessagingException;
import java.io.IOException;

public abstract class FetcherEngine implements PluggableEngine {
    protected ResterEngine resterEngine;
    protected CoreEngine coreEngine;
    protected LoginEngine loginEngine;
    protected GridDriverEngine driverEngine;

    public String getFetchingUrl() {
        return null;
    }

    public String fetch(String url) throws IOException, InterruptedException, MessagingException {
        var html = fetchHtml(url);
        if (verifyHtml(html)) {
            coreEngine.updateLoginTime();
            String json = fetchSkuProperties(html);
            return html;
        }

        if (verifyPageNotFound(html)) {
            return GlobalVariables.PRODUCT_PAGE_NOT_FOUND;
        }

        loginEngine.login();
        driverEngine.getDriver().get(url);

        return driverEngine.getDriver().getPageSource();

    }

    protected abstract String fetchHtml(String url) throws IOException;

    protected abstract boolean verifyHtml(String html);

    protected abstract boolean verifyPageNotFound(String html);

    protected abstract String fetchSkuProperties(String html) throws JsonProcessingException;

    @Override
    public void plugIn(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
        this.resterEngine = this.coreEngine.getResterEngine();
        this.loginEngine = this.coreEngine.getLoginEngine();
        this.driverEngine = this.coreEngine.getDriverEngine();
    }
}
