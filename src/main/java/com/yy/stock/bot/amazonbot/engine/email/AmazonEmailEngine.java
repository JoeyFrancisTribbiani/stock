package com.yy.stock.bot.amazonbot.engine.email;

import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.email.EmailEngine;
import org.jetbrains.annotations.NotNull;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.search.*;
import java.io.IOException;

//@Component
//@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public class AmazonEmailEngine extends EmailEngine {
    protected CoreEngine coreEngine;

    @Override
    @Retryable(value = {Exception.class}, maxAttempts = 6, backoff = @Backoff(maxDelay = 16, random = true))
    public @NotNull String getRegisterEmailVerifyCode(String email, String password) throws MessagingException, IOException {
        SearchTerm notSeenTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        SearchTerm andTerm = new AndTerm(new SearchTerm[]{
                new FromStringTerm("Amazon"),
                new SubjectTerm("Your Amazon verification code"),
                notSeenTerm
        });
        System.out.println("getEmailVerifyCode run 1 time...");
        String content = reciveIMAPmail(email, password, andTerm);
        String cssSelector = "body > div.body-wrapper > table > tbody > tr > td > div.notice-wrap.margin-bottom > table > tbody > tr > td > div > table > tbody > tr > td > div > div > div.code";
        String code = getHtmlTagContent(content, cssSelector);
        return code;
    }

    @Override
    @Retryable(value = {Exception.class}, maxAttempts = 6, backoff = @Backoff(maxDelay = 16, random = true))
    public @NotNull String getLoginEmailVerifyCode(String email, String password) throws MessagingException, IOException {
        SearchTerm notSeenTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        SearchTerm andTerm = new AndTerm(new SearchTerm[]{
                new FromStringTerm("Amazon"),
                new SubjectTerm("Verification Code From Amazon"),
                notSeenTerm
        });
        System.out.println("getEmailVerifyCode run 1 time...");
        String content = reciveIMAPmail(email, password, andTerm);
//        String cssSelector = "body > div.body-wrapper > table > tbody > tr > td > div.notice-wrap.margin-bottom > table > tbody > tr > td > div > table > tbody > tr > td > div > div > div.code";
        String cssSelector = "body > table > tbody > tr > td > div:nth-child(2) > div:nth-child(1) > strong:nth-child(2) > span";
        String code = getHtmlTagContent(content, cssSelector);
        return code;
    }

    /**
     * @param coreEngine
     */
    @Override
    public void plugIn(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
    }
}
