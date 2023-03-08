package com.yy.stock.test;

import cn.hutool.core.lang.Assert;
import com.yy.stock.bot.helper.MessageHelper;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MessageHelperTest {

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
}