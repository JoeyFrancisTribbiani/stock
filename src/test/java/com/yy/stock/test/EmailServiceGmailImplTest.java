package com.yy.stock.test;

import com.yy.stock.common.email.EmailServiceGmailImpl;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.IOException;

class EmailServiceGmailImplTest {
    @Test
    void sendSimpleMail() {
    }

    @Test
    void sendHtmlMail() {
    }

    @Test
    void reciveIMAPmail() throws MessagingException, IOException {
        EmailServiceGmailImpl s = new EmailServiceGmailImpl();
        String code = s.getEmailVerifyCode("willowwu123@gmail.com", "nvqwbcmkgcsmlcog");
        System.out.println(code);
    }

    @Test
    void testParseMoney() {
        var text = "US $7.49";
        text = text.substring(4);
        text = text.replace(',', '.');
        var d = Double.parseDouble(text);
    }

}