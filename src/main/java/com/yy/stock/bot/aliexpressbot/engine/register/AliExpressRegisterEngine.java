package com.yy.stock.bot.aliexpressbot.engine.register;

import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.driver.InstructionExecutor;
import com.yy.stock.bot.engine.email.EmailEngine;
import com.yy.stock.bot.engine.fetcher.FetcherEngine;
import com.yy.stock.bot.engine.register.RegisterEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.common.util.PasswordGenerator;
import com.yy.stock.entity.EmailAccount;
import lombok.Getter;
import org.openqa.selenium.interactions.Actions;

import javax.mail.MessagingException;
import java.io.IOException;

@Getter
public abstract class AliExpressRegisterEngine extends RegisterEngine {
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

    @Override
    public void register(EmailAccount emailAccount) throws IOException, InterruptedException, MessagingException {
        var registerUrl = "https://aliexpress.us/";
        driverEngine.getDriver().get(registerUrl);
        coreEngine.closeAdPop();
        var rightTopUserIcon = instructionExecutor.getByClassName("user-account-port");
        Actions action = new Actions(driverEngine.getDriver());
        action.moveToElement(rightTopUserIcon).perform();
        Thread.sleep(1000);

        var rightTopRegisterButton = instructionExecutor.getByClassName("join-btn");
        rightTopRegisterButton.click();
        Thread.sleep(3000);

        var emailInput = instructionExecutor.getByXpath("//*[@id=\"batman-dialog-wrap\"]/div/div/div[1]/div[2]/div/div/div/div[2]/span[1]/span[1]/input");
        var passwordInput = instructionExecutor.getByXpath("//*[@id=\"batman-dialog-wrap\"]/div/div/div[1]/div[2]/div/div/div/div[3]/div/span/span[1]/input");

        emailInput.sendKeys(emailAccount.getEmail());
        Thread.sleep(1000);
        var p = PasswordGenerator.randomPassword(16);
        passwordInput.sendKeys(p);
        Thread.sleep(1000);
        var registerButton = instructionExecutor.getByXpath("//*[@id=\"batman-dialog-wrap\"]/div/div/div[1]/div[2]/div/div/div/button");
        registerButton.click();
        Thread.sleep(3000);

        var verifyInputPanels = instructionExecutor.getByClassName("batman-verify");
        var verifyInputList = instructionExecutor.listByRelativeXpath(verifyInputPanels, "//input");

        //q:字符英文怎么说
        //a:character

        var emailCodeHas4Characters = emailEngine.getRegisterEmailVerifyCode(emailAccount.getEmail(), emailAccount.getThirdAppLoginPassword());
        verifyInputList.get(0).sendKeys(emailCodeHas4Characters.substring(0, 1));
        verifyInputList.get(1).sendKeys(emailCodeHas4Characters.substring(1, 2));
        verifyInputList.get(2).sendKeys(emailCodeHas4Characters.substring(2, 3));
        verifyInputList.get(3).sendKeys(emailCodeHas4Characters.substring(3, 4));

        Thread.sleep(1000);

        var confirmVerifyButton = instructionExecutor.getByXpath("//*[@id=\"batman-dialog-wrap\"]/div/div/div/div[2]/div/div/div/button");
        confirmVerifyButton.click();
        Thread.sleep(18000);


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