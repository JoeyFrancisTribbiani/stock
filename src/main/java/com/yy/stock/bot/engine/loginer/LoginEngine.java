package com.yy.stock.bot.engine.loginer;

import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.driver.InstructionExecutor;
import com.yy.stock.bot.engine.email.EmailEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.mail.MessagingException;
import java.io.IOException;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Accessors(chain = true)
public abstract class LoginEngine {
    protected CoreEngine coreEngine;
    protected EmailEngine emailEngine;
    protected GridDriverEngine driverEngine;
    protected ResterEngine resterEngine;
    protected InstructionExecutor instructionExecutor;

    public LoginEngine(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
        this.driverEngine = coreEngine.getDriverEngine();
        this.emailEngine = coreEngine.getEmailEngine();
        this.resterEngine = coreEngine.getResterEngine();
        this.instructionExecutor = driverEngine.getExecutor();
    }

    protected boolean isLogined() throws InterruptedException, IOException {
        var html = resterEngine.getStringResponse(coreEngine.urls.testLogin);
        return testLoginedHtml(html);
    }


    public void login() throws IOException, InterruptedException, MessagingException {
        if (isLogined()) {
            return;
        }
        if (loginUseCookie()) {
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
}