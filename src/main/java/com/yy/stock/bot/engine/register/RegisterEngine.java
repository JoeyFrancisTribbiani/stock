package com.yy.stock.bot.engine.register;

import com.yy.stock.bot.engine.PluggableEngine;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.driver.InstructionExecutor;
import com.yy.stock.bot.engine.email.EmailEngine;
import com.yy.stock.bot.engine.fetcher.FetcherEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.entity.EmailAccount;
import lombok.Getter;

import javax.mail.MessagingException;
import java.io.IOException;

@Getter
public abstract class RegisterEngine implements PluggableEngine {
    protected CoreEngine coreEngine;
    protected EmailEngine emailEngine;
    protected GridDriverEngine driverEngine;
    protected ResterEngine resterEngine;
    protected InstructionExecutor instructionExecutor;
    protected FetcherEngine fetcherEngine;

    protected boolean isLogined() throws InterruptedException, IOException {
        var html = resterEngine.getStringResponse(coreEngine.urls.testLogin);
        return testLoginedHtml(html);
    }


    public void register(EmailAccount emailAccount) throws IOException, InterruptedException, MessagingException {
        if (isLogined()) {
            whenLoginSuccessfully();
            return;
        }
        if (loginUseCookie()) {
            whenLoginSuccessfully();
            return;
        }
        loginUsePassword();
        whenLoginSuccessfully();
        coreEngine.updateBotCookie();
        coreEngine.updateLoginTime();
    }

    protected abstract boolean testLoginedHtml(String html) throws InterruptedException, IOException;

    protected abstract boolean loginUseCookie();

    protected abstract void loginUsePassword() throws InterruptedException, IOException, MessagingException;

    protected abstract void whenLoginSuccessfully() throws InterruptedException;

    @Override
    public void plugIn(CoreEngine plugBaseEngine) {
        this.coreEngine = plugBaseEngine;
        this.driverEngine = this.coreEngine.getDriverEngine();
        this.instructionExecutor = coreEngine.getDriverEngine().getExecutor();
        this.resterEngine = coreEngine.getResterEngine();
        this.fetcherEngine = coreEngine.getFetcherEngine();
        this.emailEngine = coreEngine.getEmailEngine();
    }

}