package com.yy.stock.test;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.bot.aliexpressbot.engine.email.AliExpressEmailEngine;
import com.yy.stock.bot.aliexpressbot.model.sku.skucomponent.AliExpressSkuComponent;
import com.yy.stock.bot.aliexpressbot.model.sku.skumodule.AliExpressSkuModule;
import com.yy.stock.bot.engine.driver.ChromeDriverEngine;
import com.yy.stock.bot.engine.driver.MyCookie;
import com.yy.stock.bot.helper.MessageHelper;
import com.yy.stock.common.util.PasswordGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

class MessageHelperTest {

    public static byte[] unGZip(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {
            byte[] buf = new byte[4096];
            int len = -1;
            while ((len = gzipInputStream.read(buf, 0, buf.length)) != -1) {
                byteArrayOutputStream.write(buf, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            byteArrayOutputStream.close();
        }
    }


    @Test
    void testPasswordGenerator() {
        var pass = PasswordGenerator.randomPassword(16);
        System.out.println(pass);
    }

    @Test
    void testEmailVerify() throws MessagingException, IOException {
        var emailEngine = new AliExpressEmailEngine();
        var code = emailEngine.getRegisterEmailVerifyCode("sa6842946@gmail.com", "qaynjtlduaramgmx");
        System.out.println(code);
    }

    @Test
    void testLoginEmailVerify() throws MessagingException, IOException {
        var emailEngine = new AliExpressEmailEngine();
        var code = emailEngine.getLoginEmailVerifyCode("sa6842946@gmail.com", "qaynjtlduaramgmx");
        System.out.println(code);
    }

    @Test
    void testChromeDiver() throws InterruptedException {
        var dList = new ArrayList<ChromeDriver>();
        for (int i = 0; i < 5; i++) {
            var driverManager = new ChromeDriverEngine();
            var driver = driverManager.getDriver();
            dList.add(driver);
            driver.get("https://www.baidu.com");
            Thread.sleep(10000L);
        }
        for (int i = 0; i < 5; i++) {
            dList.get(i).quit();
            Thread.sleep(5000L);
        }
    }

    @Test
    void testGetJavascript() throws IOException {
        //获取根目录(绝对路径)
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) path = new File("");
        System.out.println("path:" + path.getAbsolutePath());

        String html = new String(Files.readAllBytes(Paths.get("./HELP.html")));
        Document doc = Jsoup.parse(html);
        Elements tds = doc.getElementsByTag("script"); // 标识获取html中第一个<script>标签
        for (var element : tds) {
            var content = element.data();
            content = content.trim();
            if (content.startsWith("window.runParams")) {
                var jsonStr = content.split("window.runParams = \\{")[1];
                jsonStr = jsonStr.trim();
                jsonStr = content.split("data: ")[1];
                jsonStr = jsonStr.split("csrfToken: ")[0];
                jsonStr = jsonStr.trim();
                jsonStr = StringUtils.removeEnd(jsonStr, ",");
                System.out.println("json:" + jsonStr);
            }
        }

//        String data = tds.toString().replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", "\n\t"); // 去除html中的标签

    }

    @Test
    void getValueFromJsonMessage() {
        String message = "{\"message\":{\"method\":\"Network.requestWillBeSentExtraInfo\",\"params\":{\"associatedCookies\":[{\"blockedReasons\":[\"NotOnPath\",\"DomainMismatch\"],\"cookie\":{\"domain\":\"cart.lazada.sg\",\"expires\":-1,\"httpOnly\":false,\"name\":\"client_type\",\"path\":\"/cart/api\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":true,\"size\":18,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"desktop\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\"member.lazada.sg\",\"expires\":-1,\"httpOnly\":false,\"name\":\"client_type\",\"path\":\"/user/api\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":true,\"size\":18,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"desktop\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\"member.lazada.sg\",\"expires\":-1,\"httpOnly\":false,\"name\":\"client_type\",\"path\":\"/user\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":true,\"size\":18,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"desktop\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\"member.lazada.sg\",\"expires\":1710740788.163549,\"httpOnly\":false,\"name\":\"_uab_collina\",\"path\":\"/user\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":36,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"167618078816157191110876\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1707745586.566325,\"httpOnly\":false,\"name\":\"lzd_cid\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":false,\"size\":43,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"e9a6cbaa-4111-47fe-a481-a7ca3edc8435\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":-1,\"httpOnly\":true,\"name\":\"lzd_sid\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":true,\"size\":39,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"1dea0e4c078b3567cfa9952dffe4c394\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1710740786.566488,\"httpOnly\":false,\"name\":\"anon_uid\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":false,\"size\":40,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"d92f3d897d0f2045b529b1ee991bb209\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":-1,\"httpOnly\":true,\"name\":\"_tb_token_\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":true,\"size\":23,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"eeb319a6838b4\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1707716788,\"httpOnly\":false,\"name\":\"t_uid\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":37,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"d92f3d897d0f2045b529b1ee991bb209\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1678772788,\"httpOnly\":false,\"name\":\"hng\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":19,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"SG|en-SG|SGD|702\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\"member.lazada.sg\",\"expires\":1678772788,\"httpOnly\":false,\"name\":\"userLanguageML\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":16,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"en\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1710740790.988152,\"httpOnly\":false,\"name\":\"t_fv\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":false,\"size\":17,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"1676180790987\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1676182590,\"httpOnly\":false,\"name\":\"t_sid\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":false,\"size\":37,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"vTtZ0vbzvJTu1dXYajfIR6tyWskvhAUp\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1676182590,\"httpOnly\":false,\"name\":\"utm_channel\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":false,\"size\":13,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"NA\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1710740793.082469,\"httpOnly\":false,\"name\":\"cna\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":false,\"size\":27,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"OWtvHCWh3mcCAWenhm/1bVbY\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".member.lazada.sg\",\"expires\":1710740793.356423,\"httpOnly\":false,\"name\":\"G_ENABLED_IDPS\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":20,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"google\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1676267193,\"httpOnly\":false,\"name\":\"xlly_s\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":false,\"size\":7,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"1\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1683956795,\"httpOnly\":false,\"name\":\"_gcl_au\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":32,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"1.1.1229127239.1676180795\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\"member.lazada.sg\",\"expires\":1691732801.842125,\"httpOnly\":false,\"name\":\"_bl_uid\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":35,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"5blFtek80g6y6jr1gspp4ykx3tOs\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1691732792,\"httpOnly\":false,\"name\":\"tfstk\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":70,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"cDNVBQf3Cfq52_UOvbcaL5ecbNMAZYZgAQutnGgotH4DzokcihxtrAhZz2Lyjxf..\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1691732792,\"httpOnly\":false,\"name\":\"l\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"secure\":false,\"session\":false,\"size\":106,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"fBMiQjwrT8IwF3thBOfaPurza779sIRXouPzaNbMi9fP_DfH5C-XX6-OFBYMCnhVEsRWL38_FFteBJTgFy4ThUiDCUVQoWyx3d6g-NkQv\"}},{\"blockedReasons\":[],\"cookie\":{\"domain\":\".lazada.sg\",\"expires\":1691732792,\"httpOnly\":false,\"name\":\"isg\",\"path\":\"/\",\"priority\":\"Medium\",\"sameParty\":false,\"sameSite\":\"None\",\"secure\":true,\"session\":false,\"size\":67,\"sourcePort\":443,\"sourceScheme\":\"Secure\",\"value\":\"BAMDcp2rZIK8fih2WCh9Cp1CksGteJe6VaANIzXgXmLZ9CIWnksFC9Lia4S64u-y\"}}],\"clientSecurityState\":{\"initiatorIPAddressSpace\":\"Local\",\"initiatorIsSecureContext\":true,\"privateNetworkRequestPolicy\":\"PreflightWarn\"},\"connectTiming\":{\"requestTime\":359398.45513},\"headers\":{\":authority\":\"member.lazada.sg\",\":method\":\"POST\",\":path\":\"/user/api/login?_bx-v=2.2.3\",\":scheme\":\"https\",\"accept\":\"application/json, text/plain, */*\",\"accept-encoding\":\"gzip, deflate, br\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"bx-sys\":\"ua-l:no__um-l:lazada__js:https://laz-g-cdn.alicdn.com/\",\"bx-ua\":\"225!Ml9bupzWooiU9S6YniyoBrwoIBX32cWO5eW79+at7RqpStOMHUxXYl38J1Pc48GsxaxFhuSiWnyVFPDoWXfKrlE8OxbZJExC/rrkj4relKy0m2CYQaLcqPUrbuaaa9tC5yH12fiXUwnlyQvnmo10oRbGoJiRDG/+f460Qk0UDMzhfiN2TEVuqHfeSJ3wDGHzfeGeuiT1h8XxfkFDoFS1jcI4oL3jDGHzfeG0QEDdEMX3NZ+dLtpOYB+RoL3RDfH+fej0rjDr485MMIJBoDC1mLsRS9RogvrWxhaLCGok9sNQOf5CbU9EVxf4oJiRDGH+fej0Qz0KEM5hf/HAI2oQjsrXVhii9I5vWbCeAUiOeY+QOdt0iI9ql7I3yS0UekFw6cRxW3YAVLr7117g3tq8qHkZ5w4/g1mcwQxxvUCYeHgjmfJ3dvjuB27gnq0TbKNMAclPuDQ1yXAARKrFHOoSrrN1bAxZy+ORwQ36uiYj/X/PfkXe31SNw8O44hDTy+JsAbeIlUeNH22qfdKF+NCYAAfd5Vb49tgM6YexkTbvgJO7GKI5FzjvY5NZnV4EgEJM2VhOOpTq/r1MIZy0i1l2AH8tH6Co9ZNAMxSlm0wMe/k81z8Dome1scmnVP3jDGH+fej0QtQnD654fidCxp98fJoy0MGtUQ6pSRaXgs0Kmg1Nl9uxitfK/JiByI+k00YVw38XE3wmKp9UGQlaP3qceQDNbCE5xMKpBZD2H6E6GXq/4KkVB/xEelZ9pBQatOla6shX/DF641udgNPW6ISGCaQ7FSkQpjnoXZieUA75eZysrdbYmtkr0utMqbK2f9f1xp6yv+QGezpGoh0wcinZ+5RcET39HXC/kMqd0QquKa0+D8Fa2lUcQcCqquv9WahYrcZEtn8H6IYVaz92NrGeWddedGpjs2W0shutBOPwP3QolQGkKw7ambADxOmh+8uuTy3xMWS4eywpNuy5xaE3XITSHy0ygVrkhqWWu7jRKi85iDZR3r69J9WoMZ77OP9Si4+eaUtmnC+zrfc4FrbxvIFFcySylihyGg7+/oh1AvmJmD3Q4RSRsOYQhsWfhj5MXCOpzf2ikiyUBFfsyPhXmcPc6bn0pxUY1FGULrjrIx4t2g1mBpulNcs58liv+Q+QH9Rxe8FF1c+06l2gN6A0sTMvOceV/dEEmFOGv5MH0NWTf5g8ZlzlFeJrXhbGy12ngZakGrq+3J37jSOTBGrMxTh8CGlAjT8GSqoytt7bkavi295COIYg8tpxcvKZ6AGi9uHhfCOYwHJewJ8yESf4xooMQFQ3kn3PcinyZ7F/hqmqqpDfpOaV+1SqBa+wvDxpiPRoOk0hyV92R1ZS/fb4XEXnWM1/bL7Q83Akre8CTb4J6BWuBpkw303A77yyn5FLCZi4QzGdUI2owr42JezSwcp=\",\"bx-umidtoken\":\"G0E0E205A3A4B2E96007DDE1404BB1F5D1943685A0C962C0572\",\"bx-v\":\"2.2.3\",\"content-length\":\"85\",\"content-type\":\"application/json\",\"cookie\":\"client_type=desktop; client_type=desktop; _uab_collina=167618078816157191110876; lzd_cid=e9a6cbaa-4111-47fe-a481-a7ca3edc8435; lzd_sid=1dea0e4c078b3567cfa9952dffe4c394; anon_uid=d92f3d897d0f2045b529b1ee991bb209; _tb_token_=eeb319a6838b4; t_uid=d92f3d897d0f2045b529b1ee991bb209; hng=SG|en-SG|SGD|702; userLanguageML=en; t_fv=1676180790987; t_sid=vTtZ0vbzvJTu1dXYajfIR6tyWskvhAUp; utm_channel=NA; cna=OWtvHCWh3mcCAWenhm/1bVbY; G_ENABLED_IDPS=google; xlly_s=1; _gcl_au=1.1.1229127239.1676180795; _bl_uid=5blFtek80g6y6jr1gspp4ykx3tOs; tfstk=cDNVBQf3Cfq52_UOvbcaL5ecbNMAZYZgAQutnGgotH4DzokcihxtrAhZz2Lyjxf..; l=fBMiQjwrT8IwF3thBOfaPurza779sIRXouPzaNbMi9fP_DfH5C-XX6-OFBYMCnhVEsRWL38_FFteBJTgFy4ThUiDCUVQoWyx3d6g-NkQv; isg=BAMDcp2rZIK8fih2WCh9Cp1CksGteJe6VaANIzXgXmLZ9CIWnksFC9Lia4S64u-y\",\"origin\":\"https://member.lazada.sg\",\"referer\":\"https://member.lazada.sg/user/login?spm=a2o42.10453684.header.d5.795a3122Utb2nj&redirect=https%3A%2F%2Fwww.lazada.sg%2Fshop%2Ftaobao%2F%3Fspm%3Da2o42.home.pcChannels.channel_1.4ed346b5kmULYu%26scm%3D1003.4.icms-zebra-5000357-6741155.OTHER_6502075465_7674519\",\"sec-ch-ua\":\"\\\"Not_A Brand\\\";v=\\\"99\\\", \\\"Google Chrome\\\";v=\\\"109\\\", \\\"Chromium\\\";v=\\\"109\\\"\",\"sec-ch-ua-mobile\":\"?0\",\"sec-ch-ua-platform\":\"\\\"macOS\\\"\",\"sec-fetch-dest\":\"empty\",\"sec-fetch-mode\":\"cors\",\"sec-fetch-site\":\"same-origin\",\"user-agent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36\",\"x-csrf-token\":\"eeb319a6838b4\",\"x-requested-with\":\"XMLHttpRequest\",\"x-ua\":\"140#SPFDEMVYzzFmMQo22xfTA6SoYRMXaWvmF7Hf0U6hYFN6jhN/uU2cCke6uXfVUnguBgJB6mRO/d3YOwBKbcuEW6hqzznvRmDzNmSzzZzKIjnulFzx2DD3VthqzFLzhpbUb12rzIrbL8/qlbr8STH+VphuzFFi218KlpOxz8rIV2hqlzrD2PD+VphPzF2iL28KlpsxxPryV2nq3Qrx2PcKV6EtzQrV2XY4l3MDzP2iVOgtlzFxmQgkzjmijDapV4qP8GF1qtZM4//k27knA7565oK3kgBu52SEC5ecwQAeWRGA+BHJd2tbnLcWWQwCZ/4exqPfvoBz8esUnde2AtmYLE+8l7J93V1RtWxZDNYiKXZlnt29K+s0TlHwlA022gnsj5N8gFZ+NoGKiDMGLTRLIK7iI1hyJntkYI7p3Zd/YYbaKdfv3XvLVmKtSVk+k7DzLNyob1qVq9DODc01LG84YFbmIV0zA5BpFiKs0YvJc6KX3aeThzRPWyHk0wjZOsiQ6L5aTYFlXzUOAWf9Npce6R3y4tcWXkesdQph8hAzJ/Oto4zwZ58B8pEUk82sWi+MN9Hzjg7iAOD3leHO17wXCjqojTm+vbr0p/Z9jpcRfrW5KvVjLElm9zHXS7iSKLFVqf0DWdco8SP585bX1v7T0VvC8IXphHjVfPcC8bzRsCO2DUHu0S2bwePxTSuPhTRW9OCeOjWppdNdRWKLH2ADhWnu897+Fn8pnub3U2TdPSc7rxGY50QzJ+UxL3VFzofFdyZ6hd/eCi4OHLCWr1zUrwVzuXF8ubD5l9zDMXbTLQRbkS/NiAjI3CogOKTg9UtxmQPrqhrWIZz9og2C3EbhChdkSeOw2qAZqGjAHhWFmJwJc6fn5ODyG4873eimmDruI4NKSOFZU58vUna33EJpVkXgyuEAApnmnx5Ev0gCGf4tNs9iA39m6ukeZUIVdbSeSLn6qZ9oRRr2u0eRM0lDaqrKvpmmjIqta8cO4FUeo5cpMryRL3T39GqwuF/ft16cRke+AI0ATHJeBIKuzOd6Lm9sr+6TjNeVTjSfs+3YHqqxPJui2eavoRA9QLOCSlLoIbwsP18l56GCjiBBaMDm2L9KTSycG+vxOdqJ+6juH7iMPq1cT+V/2C2JXIePGnSWLGcPVbLBTxxcXDU1KIsSTKSxWZOyQV7BtrurtJZ6IkIMEsEIfcmjirNVP3kud8/HVHB19Ugk\",\"x-umidtoken\":\"T2gAnv0QaSll61nmHeUwAebf-wTOOG1YfGBU_z2drc150efNmE67HKWaljijGnr7EmE=\"},\"requestId\":\"40287.177\"}},\"webview\":\"2FF3A364C238120A1881CF02A05A9F72\"}";
        String v = MessageHelper.getValueFromJsonMessage(message, "x-umidtoken");
        System.out.println(v);
    }

    @Test
    void testDatetime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -2);
        System.out.println(c.getTime());

    }

    @Test
    void testCleanName() {
        String name = "fdaf&'fdfd$jk";
        String regEx = "[%&',;=?$\\x22]+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(name);
        var s = matcher.replaceAll("").trim();
        Assert.isTrue(s.equals("fdaffdfdjk"));

    }

    @Test
    void testCleanPhone() {
        String phone = "+61 3349029 3";
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phone);
        phone = matcher.replaceAll("");
        Assert.isTrue(phone.equals("6133490293"));

        if (phone.charAt(phone.length() - 9) != '9' && (phone.length() == 10 || phone.length() == 12)) {
            var sb = new StringBuilder(phone);
            phone = sb.insert(phone.length() - 8, "9").toString();
        }
        if (phone.startsWith("55") && phone.length() > 11) {
            phone = phone.substring(2);
        }
        if (phone.startsWith("0")) {
            phone = phone.substring(1);
        }
        Assert.isTrue(phone.equals("61933490293"));
    }

    @Test
    void testBrasilMoney() {
        var text = "R$ 6,17";
        text = text.substring(3);
        text = text.replace(',', '.');
        Float n = Float.parseFloat(text);
        Assert.isTrue(n.floatValue() == 6.17f);
    }

    @Test
    void testSplit() {
//        var name = "xiaohao_br-20230306133337.txt";
//        var nameWords = name.split("-");
//        var withExt = nameWords[1];
        var withExt = "20230306133337.txt";
        var s = withExt.split("\\.");
        s = withExt.split(".");
//        nameWords[1] = s[0];

    }

    @Test
    void testCookieStr() throws JsonProcessingException {
        var cookieStr = "[{\"name\":\"ali_apache_track\",\"value\":\"mt=1|ms=|mid=uk2221891091rzaae\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010226000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"_hvn_login\",\"value\":\"13\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1709208620000,\"sameSite\":null,\"httpOnly\":false,\"secure\":true},{\"name\":\"xman_us_t\",\"value\":\"x_lid=uk2221891091rzaae&sign=y&rmb_pp=nevenmore@outlook.com&x_user=1bScqlQS8NFR85BJ2xnbGVDgDy+VaMpVwBLBO6Itx8s=&ctoken=1dfhp9_vye7jl&l_source=aliexpress\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1686226220000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"aep_common_f\",\"value\":\"HszgPUcmrerH1F4MZu+RM2lQmvif+UqoAaX2oEt9Z6Rd5ZV6WmtUkQ==\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010220000,\"sameSite\":\"None\",\"httpOnly\":true,\"secure\":true},{\"name\":\"JSESSIONID\",\"value\":\"22DB009EC2E8CC34E601CD4910128AB6\",\"path\":\"/\",\"domain\":\"www.aliexpress.com\",\"expiry\":null,\"sameSite\":null,\"httpOnly\":true,\"secure\":false},{\"name\":\"_ym_visorc\",\"value\":\"b\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1678452034000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"x_router_us_f\",\"value\":\"x_alimid=3831282040\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010220000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"_ym_uid\",\"value\":\"1678450232397516925\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1709986232000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"xman_us_f\",\"value\":\"zero_order=n&x_locale=en_US&x_l=1&x_user=UK|nevenmore|user|ifm|3831282040&x_lid=uk2221891091rzaae&x_c_chg=1&x_as_i=%7B%22aeuCID%22%3A%22%22%2C%22cookieCacheEffectTime%22%3A1678450523484%2C%22isCookieCache%22%3A%22Y%22%2C%22ms%22%3A%220%22%7D&acs_rt=e167ca1f52ec475ebf4114ad075d28d7\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010226000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"_ym_isad\",\"value\":\"2\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1678522232000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"cna\",\"value\":\"+QuSHHQ9ilQCAWeyNgveJ8cF\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010169000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"_m_h5_tk_enc\",\"value\":\"0b859e50605c046b1f8a260f6ab68857\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1679055024000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"_fbp\",\"value\":\"fb.1.1678450226072.1254795552\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1686226226000,\"sameSite\":\"Lax\",\"httpOnly\":false,\"secure\":false},{\"name\":\"cto_bundle\",\"value\":\"uUF6o18wQjJDbm1kJTJGbTR3QkJIaVpNaWpkU29nRUtPY0huNjAxZU1GVk9mMWZnNXlhTU5TeXhad2tPV1B1M2ZqWXQ3RjglMkYwQ05kQm9PVENjcVRqekl5U3VwbGg4aFkxJTJCQ29OZjF2SFBTcnU1S1A0TzhjUEh0ZlhRY0JURFhFMFlpcXQ1dVU3SFIxbGFldkdXU082cTVPb2NwTkElM0QlM0Q\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1712614225000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"acs_usuc_t\",\"value\":\"acs_rt=e167ca1f52ec475ebf4114ad075d28d7&x_csrf=3u64ank1zu56\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":null,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"_m_h5_tk\",\"value\":\"390f38d41a5324e8e405a6434f88746c_1678452834409\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1679055024000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"aep_usuc_f\",\"value\":\"site=glo&c_tp=USD&x_alimid=3831282040&isb=y&region=CN&b_locale=en_US\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010226000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"xlly_s\",\"value\":\"1\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1678536569000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"intl_locale\",\"value\":\"en_US\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":null,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"xman_t\",\"value\":\"221dY9sSN3d7qSvxpVQ/UgcbNHmAKxWmBtlyjiSI7CI6uTl3OpfSE2CWsklAsR3LECoHwMl6edikG85cZoGMGnPCmgCjcLzoomxkj1rZEpab84QbinQycUmE+ecb9Fd+5gp0MbA9oPc+fpmECJiUiBESNxCkSeH7FmGnHnINqK1dX/rYnNhzRU4NhU2uyeFBNzSM3YAJiKCkFxhTfzMrnrWej6rjY0RkZzFQ3AQiw9iD+Q99tqoK+Shjx2JD71rpl91j7tgYJ78Nn8qdHamR9OPi+B4GTT0n7vIltElJkoXulLcURJGCvK6lfkJZyFRtlOykpw8xJ3tNeogHuYfXJ7EYWVpvsl68BT0xx1kW6ng91vBz5BCa10OB7JoC+JHXYmTZom4G0Nbdw+YTmEL6yMx467gTKfpfHAkxtSMMm7h37fXVwRJWgsLzkkZf8itGPZ9vHwoFSdIiqoEaY0Fsz/4CDihj4EElKwclle0Q9Rv3IY7ef48JjEnjlZpbFVGw5kmU9SfHmaXqR9Jn/pXtOUSFfAJ0BeX9739dHQOauI7KB8Lu/tN3akSZdw5RwOJaiIkoxmaVorr8Pm0UhU2wbN1zIIy9raysveu4VKYOIOv8Pd7sTcDAFSxPzbujS6Mw+9NLgnOeETJapOTrUVwDlw/jafZb6O7ceD5CJiHX4jwP8Z6w2yPVz+DL5pIZhPhot2xiX9oH7RXR9ycxqIYprszHcpgxOcMTAag7qhx1VAN+gTF/4Xtwfg==\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1686226225000,\"sameSite\":\"None\",\"httpOnly\":true,\"secure\":true},{\"name\":\"ali_apache_id\",\"value\":\"33.50.246.208.1678450166652.473767.2\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010166000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"tfstk\",\"value\":\"ch_CBJ66ZTQNqZao-pNaaOb7OqYNZilXNk9hOJYh3-lsdICCiG32huVAqYLywC1..\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1694002224000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"_ym_d\",\"value\":\"1678450232\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1709986232000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true},{\"name\":\"l\",\"value\":\"fB_yxbkcNg51J5qkXOfwFurza77tIIRAguPzaNbMi9fPOjfX5VYHX1GiFwYWCnGVFsswR38_FFteBeYBcSqEukhleSYFYMMmn_vWSGf..\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1694002224000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"havana_tgc\",\"value\":\"eyJwYXRpYWxUZ2MiOnsiYWNjSW5mb3MiOnsxMzp7ImFjY2Vzc1R5cGUiOjEsIm1lbWJlcklkIjoxNTc2NDE1Njg2MjQyLCJ0Z3RJZCI6IjdKdnk2ZWE1TG9FVlZYRFIteUltRFpnIn19fX0\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1709208620000,\"sameSite\":null,\"httpOnly\":true,\"secure\":true},{\"name\":\"_gid\",\"value\":\"GA1.2.1524856135.1678450225\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1678536625000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"ali_apache_tracktmp\",\"value\":\"W_signed=Y\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":null,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"_ga_VED1YSGNC7\",\"value\":\"GS1.1.1678450225.1.0.1678450225.0.0.0\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010225000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"intl_common_forever\",\"value\":\"SCQ8xrvFRTzLcxDikcH7LP/CphWN5RsXKcxyCPjZv90aZQJ03UKOhA==\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010226000,\"sameSite\":null,\"httpOnly\":true,\"secure\":false},{\"name\":\"_gat\",\"value\":\"1\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1678450285000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"_ga\",\"value\":\"GA1.1.1200079428.1678450225\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010225000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"_gcl_au\",\"value\":\"1.1.1604439341.1678450226\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1686226225000,\"sameSite\":null,\"httpOnly\":false,\"secure\":false},{\"name\":\"xman_f\",\"value\":\"isMxkG+69//105fHbGwyTQ69xDVzTkv4m4VkonKLVoc/MXscoUrd2DEPlxOBY8FCsYrlvt5TzyEUQdCaFKUEqnHthuGI1JclS0VK32InNsBMY4UQj8tULDsqgXbiwqojOY37VO8htb2eNePmqoWRluehNBlkIhZiWdS+We9H9+c2XxEWv8yXWLy4O0TsKoyDRMP6YbkwVi5rh75cuIAKe5BlIN8GrbZTM6hZCHK5JzJA1Ggaiw5OsmE9I0Q0cSRIXlhRy6k0tvjvNi8uEA1+wV31ac3I3WRuOXEi6HWegMapaQgdeH35WITO3cq56shB1P+ZDKjt7flT6U3Nb0IQvGWNanIcNkYZterV3j98ferAHBCjEfzLzCEvZ19/bltY62oNBdXADGnHuZKx44akid0y/CED9kq0\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1713010220000,\"sameSite\":\"None\",\"httpOnly\":true,\"secure\":true},{\"name\":\"isg\",\"value\":\"BGNjWM-AA0E_Ls91mAfK68yB8qENWPeatUDtA5XAt0I51IP2HCzm60iGyqRa9E-S\",\"path\":\"/\",\"domain\":\".aliexpress.com\",\"expiry\":1694002223000,\"sameSite\":\"None\",\"httpOnly\":false,\"secure\":true}]";

//        cookieStr = cookieStr.replace("httpOnly", "isHttpOnly").replace("secure", "isSecure");
        var cc = new ObjectMapper().readValue(cookieStr, MyCookie[].class);
        Assert.notNull(cc);
    }

    @Test
    void testProductHtml() throws IOException {
        var url = "https://www.aliexpress.com";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(initHeaders());
        HttpEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                byte[].class
        );
        byte[] data = unGZip(new ByteArrayInputStream(response.getBody()));

        var result = new String(data, "UTF-8");
        System.out.println(result);
    }

    @Test
    void testSkuComponentJsonToJava() {
        //读取项目根目录的aliSKUComponent.json文件
        String json = null;
        try {
            json = FileUtils.readFileToString(new File("aliSKUComponent.json"), "UTF-8");
            //将json转为java对象AliExpressSkuComponent
            AliExpressSkuComponent aliExpressSkuComponent = new ObjectMapper().readValue(json, AliExpressSkuComponent.class);
            System.out.println(aliExpressSkuComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSkuModuleJsonToJava() {
        String json = null;
        try {
            json = FileUtils.readFileToString(new File("aliSKUModule.json"), "UTF-8");
            //将json转为java对象AliExpressSkuModule
            AliExpressSkuModule aliExpressSkuModule = new ObjectMapper().readValue(json, AliExpressSkuModule.class);
            System.out.println(aliExpressSkuModule);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpHeaders initHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "text/html;charset=UTF-8");
        headers.add("Host", "https://www.aliexpress.com/");
        headers.add("cache-control", "no-cache");
        headers.add("pragma", "no-cache");
        headers.add("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("sec-ch-ua-platform", "\"macOS\"");
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        headers.add("sec-fetch-user", "?1");
        headers.add("upgrade-insecure-requests", "1");
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.add("Origin", "hhttps://www.aliexpress.com");
        headers.add("Sec-Fetch-Site", "none");
        headers.add("Sec-Fetch-Mode", "navigate");
        headers.add("Sec-Fetch-Dest", "document");
        headers.add("Referer", "https://www.aliexpress.com");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,und;q=0.7,ru;q=0.6");
        return headers;
    }

    @Test
    void testCurrency() {
        var code = "BR";
//        var c = Currency.getInstance(code);
        var c = Currency.getInstance(new Locale("en", code)).getCurrencyCode();
//        var n = c.getDisplayName(new Locale("en", code));
        //q:Currency.getInstance(code)应该传什么参数？
        //a:传入的参数是国家代码，不是货币代码
        //q:举个例子
        //a:Currency.getInstance("CN")，返回的是人民币
        //q:那么如何获取货币代码？
        //a:Currency.getInstance("CNY")
        //q:如何通过国家代码获取货币代码？
        //a:Currency.getInstance(new Locale("en", "CN")).getCurrencyCode();
        //q:巴西的国家代码是多少？
        //a:BR
        System.out.println(c);
    }

    @Test
    void testCountryName() {

        var l = new Locale("en", "BR");
        var c = l.getCountry();
        var b = l.getDisplayCountry();
        var d = l.getDisplayCountry(new Locale("en", "BR"));
        System.out.println(l);

    }
    @Test
    void testLeafCategoryFormat(){
        var href ="https://www.amazon.sg/b/ref=dp_bc_aui_C_4?ie=UTF8&node=6400501051p";
        var paramArr = href.split("&");
        var categoryId = "";
        for(var param : paramArr){
            if(param.startsWith("node=")){
                categoryId = param.replace("node=", "");
                break;
            }
        }



        System.out.println(categoryId);
    }
}