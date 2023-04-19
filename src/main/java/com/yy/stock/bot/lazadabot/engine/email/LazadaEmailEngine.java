package com.yy.stock.bot.lazadabot.engine.email;

import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.email.EmailEngine;
import com.yy.stock.common.email.EmailServiceGmailImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.search.*;
import java.io.IOException;

//@Component
//@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public class LazadaEmailEngine extends EmailEngine {
    protected CoreEngine coreEngine;

    @Override
    @Retryable(value = {Exception.class}, maxAttempts = 6, backoff = @Backoff(maxDelay = 16, random = true))
    public @NotNull String getRegisterEmailVerifyCode(String email, String password) throws MessagingException, IOException {
        SearchTerm notSeenTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        SearchTerm andTerm = new AndTerm(new SearchTerm[]{
                new FromStringTerm("Lazada Singapore"),
                new SubjectTerm("Verify Your Email"),
                notSeenTerm
        });
        System.out.println("getEmailVerifyCode run 1 time...");
//        throw new ArrayIndexOutOfBoundsException();
        String content = reciveIMAPmail(email, password, andTerm);

        String cssSelector = "body > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > font";
        String code = EmailServiceGmailImpl.getHtmlTagContent(content, cssSelector);
        return code;
    }

    /**
     * @param email
     * @param password
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    @Override
    public String getLoginEmailVerifyCode(String email, String password) throws MessagingException, IOException {
        return null;
    }

    /**
     * @param coreEngine
     */
    @Override
    public void plugIn(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
    }
}
