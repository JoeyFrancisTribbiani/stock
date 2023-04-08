package com.yy.stock.bot.aliexpressbot.engine.loginer;

import com.yy.stock.bot.engine.loginer.LoginEngine;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Slf4j
public class AliExpressLoginEngine extends LoginEngine {
    @Override
    protected boolean testLoginedHtml(String html) throws InterruptedException, IOException {
        if (html.contains("imageList\":[")) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean loginUseCookie() {
        coreEngine.goHome();
        try {
            String currentUrl = instructionExecutor.getCurrentUrl();// 获取跳转后的url地址
            if (currentUrl.startsWith(coreEngine.urls.loginPage)) {
                var html = instructionExecutor.getPageSource();
                if (html.contains("You are now signed in to your account")) {
                    var accessNowButton = instructionExecutor.getByXpath(coreEngine.xpaths.loginAccessNowButton);
                    accessNowButton.click();
                    Thread.sleep(2000);
                    // todo 滑块验证
                    try {
                        coreEngine.solveLoginCaptcha();
                        accessNowButton.click();
                        Thread.sleep(500);
                    } catch (Exception e) {
                        log.error("滑块验证失败", e);
                    }
                    html = instructionExecutor.getPageSource();
                    if (html.contains("Your account name or password is incorrect.")) {
                        return false;
                    }

                    instructionExecutor.waitForUrl(coreEngine.urls.homePage, 18);
                    return true;
                } else {
                    return false;
                }
            }

            return currentUrl.startsWith(coreEngine.urls.homePage);
        } catch (TimeoutException e) {
            log.info("selenium超时错误,将重试...", e);
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void loginUsePassword() throws InterruptedException, IOException, MessagingException {
        coreEngine.goHome();
        try {
            if (instructionExecutor.getCurrentUrl().startsWith(coreEngine.urls.homePage)) {
                WebElement loginTopButton = instructionExecutor.getByXpath(coreEngine.xpaths.getLoginTopButton());
                loginTopButton.click();
                Thread.sleep((long) (Math.random() * 3000));

//                instructionExecutor.waitForUrl(coreEngine.urls.homePage, 30);
                while (instructionExecutor.getCurrentUrl().equals(coreEngine.urls.homePage)) {// 不断的获取地址判断一下，地址有没有变
                    Thread.sleep(1000);
                    // 页面没有跳转就让他等待，等待自己重定向到登录后的页面，然后再获取cookie时就是正确的cookie
                }
            }
            final String loginOrVerifyUrl = instructionExecutor.getCurrentUrl();// 获取跳转后的url地址

            if (loginOrVerifyUrl.contains("member.lazada.sg/user/verification-pc")) {
                verifyLoginByEmail();
            } else {
                inputAccountAndPassword();

                instructionExecutor.waitForUrl(coreEngine.urls.homePage, 30);
//                while (!instructionExecutor.getCurrentUrl().equals(coreEngine.urls.homePage)) {// 不断的获取地址判断一下，地址有没有变
//                    Thread.sleep(1000);
//                }
                coreEngine.closeAdPop();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("登录失败!");
            throw e;
        }
    }


    @Override
    protected void whenLoginSuccessfully() throws InterruptedException {
        changeRegion();
    }

    private void changeRegion() throws InterruptedException {
        coreEngine.goHome();
        String code = coreEngine.getCountryCode();
        var countryName = coreEngine.locateCountryName();
        var currency = coreEngine.locateCountryCurrency();

        Thread.sleep(6000L);
        var regionDiv = instructionExecutor.getById("switcher-info");

        var spans = instructionExecutor.listByRelativeXpath(regionDiv, ".//span");
        boolean needChange = false;
        for (var span : spans) {
            if (span.getAttribute("class").equals("ship-to")) {
                var regionCode = instructionExecutor.getByRelativeXpath(span, ".//i").getAttribute("class");
                if (!regionCode.contains(code.toLowerCase())) {
                    needChange = true;
                    break;
                }
            }
            if (span.getAttribute("class").equals("language_txt")) {
                if (!span.getText().contains("English")) {
                    needChange = true;
                    break;
                }
            }
            if (span.getAttribute("class").equals("currency")) {
//                if (!span.getText().contains("USD")) {
                if (!span.getText().contains(currency)) {
                    needChange = true;
                    break;
                }
            }
        }
        if (!needChange) {
            return;
        }


        regionDiv.click();

        Thread.sleep(2000L);
        var checkListButton = instructionExecutor.getByClassName(coreEngine.classSelector.regionCheckListButton);

        var ref = new Object() {
            WebElement selectDiv = null;
        };
        while (ref.selectDiv == null) {
            try {
//                ref.selectDiv = new WebDriverWait(Duration.ofSeconds(6)).until(d -> checkListButton.findElement(By.className("address-select")));
                ref.selectDiv = instructionExecutor.getByClassName(checkListButton, "address-select");
            } catch (Exception ex) {
                regionDiv.click();
                Thread.sleep(2000L);
            }
        }
        var links = instructionExecutor.listByTagName(checkListButton, "a");
        for (var a : links) {
            var dataRole = a.getAttribute("data-role");
            if (dataRole.equals("country")) {
                var aText = a.getText();
                if (aText.contains(countryName)) {
                    return;
                } else {
                    a.click();
                    break;
                }
            }
        }
        Thread.sleep(2000L);

        var selectItemLis = instructionExecutor.listByTagName(checkListButton, "li");
        for (var li : selectItemLis) {
            var dataName = li.getAttribute("data-name");
            if (dataName.equals(countryName)) {
                li.click();
                break;
            }
        }
        Thread.sleep(3000L);

        var languageSwitcher = instructionExecutor.getByClassName("switcher-language");
        var languageButton = instructionExecutor.getByRelativeXpath(languageSwitcher, ".//span[@class='select-item']");
        languageButton.click();
        Thread.sleep(1000L);
        var languageLis = instructionExecutor.listByRelativeXpath(languageSwitcher, ".//a[@class='switcher-item']");
        for (var a : languageLis) {
            var aText = a.getAttribute("textContent");
            if (aText.equals("English")) {
                a.click();
                break;
            }
        }
        Thread.sleep(2000L);


        var currencySwitcher = instructionExecutor.getByClassName("switcher-currency");
        var cyButton = instructionExecutor.getByRelativeXpath(currencySwitcher, ".//span[@class='select-item']");
        cyButton.click();
        Thread.sleep(1000L);
        var cyLis = instructionExecutor.listByRelativeXpath(currencySwitcher, ".//a");
        var localeCurrency = coreEngine.locateCountryCurrency();
        for (var a : cyLis) {
            var aText = a.getAttribute("data-currency");
            if (aText != null && aText.equals(localeCurrency)) {
                a.click();
                break;
            }
        }
        Thread.sleep(2000L);

        var saveButton = instructionExecutor.getByXpath(coreEngine.xpaths.regionSaveButton);
        saveButton.click();
        Thread.sleep(6000L);
    }


    private void verifyLoginByEmail() throws InterruptedException, MessagingException, IOException {
        inputAccountAndPassword();

        try {
//            new WebDriverWait(Duration.ofSeconds(6)).until(ExpectedConditions.urlContains("member.lazada.sg/user/verification-pc"));
            instructionExecutor.waitForUrl("member.lazada.sg/user/verification-pc", 6);
        } catch (Exception ex) {

        }

        List<WebElement> verify_buttons = instructionExecutor.listByClassName("verify-item");
        WebElement email_verify_button = verify_buttons.stream().filter(b -> b.getText().equals("Email Verification")).findFirst().orElse(null);
//             instructionExecutor.getByXpath( xpaths.getAccountVerifyButton());
        email_verify_button.click();

        WebElement email_send_button = instructionExecutor.getByXpath(coreEngine.xpaths.getVerifyEmailSendButton());
        email_send_button.click();

        Thread.sleep(18000);

        String accountVerifyEmailAddress = coreEngine.getBuyerAccount().getVerifyEmail();
        String accountVerifyEmailPassword = coreEngine.getBuyerAccount().getVerifyEmailPassword();
        String code = emailEngine.getEmailVerifyCode(accountVerifyEmailAddress, accountVerifyEmailPassword);

        if (code != "") {
            WebElement email_code_input = instructionExecutor.getByXpath(coreEngine.xpaths.getVerifyEmailCodeInput());
            instructionExecutor.clearAndType(email_code_input, code);

            Thread.sleep((long) (Math.random() * 3000));

            WebElement verifySubmitButton = instructionExecutor.getByXpath(coreEngine.xpaths.getVerifySubmitButton());
            verifySubmitButton.click();
        }
    }

    private void inputAccountAndPassword() throws InterruptedException {
        WebElement email_input = instructionExecutor.getByXpath(coreEngine.xpaths.getAccountInput());
        instructionExecutor.clearAndType(email_input, coreEngine.getBuyerAccount().getEmail());
        Thread.sleep((long) (Math.random() * 3000));

        WebElement password_input = instructionExecutor.getByXpath(coreEngine.xpaths.getPasswordInput());
        instructionExecutor.clearAndType(password_input, coreEngine.getBuyerAccount().getPassword());
        Thread.sleep((long) (Math.random() * 3000));

        WebElement loginButton = instructionExecutor.getByXpath(coreEngine.xpaths.getLoginButton());
        loginButton.click();
        Thread.sleep(2000);

        // todo 滑块验证
        try {
            coreEngine.solveLoginCaptcha();
            loginButton.click();
        } catch (Exception e) {
            log.error("滑块验证失败", e);
        }

        Thread.sleep(8888);
    }
}
