package com.yy.stock.bot.engine.loginer;

import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.driver.InstructionExecutor;
import com.yy.stock.bot.engine.email.EmailEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import jakarta.annotation.Resource;

import javax.mail.MessagingException;
import java.io.IOException;

public abstract class LoginEngine {
    protected CoreEngine coreEngine;
    @Resource
    protected EmailEngine emailEngine;
    @Resource(name = "driverEngine")
    protected GridDriverEngine driverEngine;
    @Resource
    protected ResterEngine resterEngine;
    protected InstructionExecutor instructionExecutor;

    public LoginEngine(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
        this.instructionExecutor = driverEngine.getExecutor();
    }

    protected boolean isLogined() throws InterruptedException, IOException {
        var html = resterEngine.getStringResponse(coreEngine.urls.testLogin);
        return testLoginedHtml(html);
    }


    public void Login() throws IOException, InterruptedException, MessagingException {
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