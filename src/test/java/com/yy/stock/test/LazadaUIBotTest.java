package com.yy.stock.test;

import com.yy.stock.bot.base.LoginRequest;
import com.yy.stock.bot.helper.SeleniumHelper;
import com.yy.stock.bot.lazadaui.LazadaUIBot;
import com.yy.stock.bot.lazadaui.model.address.CreateAddressRequestModel;
import com.yy.stock.bot.lazadaui.model.cart.AddCartRequestModel;
import com.yy.stock.bot.lazadaui.selector.LazadaXpaths;
import com.yy.stock.common.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class LazadaUIBotTest {
    private final LazadaUIBot lazadaUIBot;
    @Autowired
    private LazadaXpaths xpaths;


    @Autowired
    public LazadaUIBotTest(LazadaUIBot lazadaUIBot) {
        this.lazadaUIBot = lazadaUIBot;
    }

    @Test
    void testBeans() {
        System.out.println(xpaths);
    }

    @Test
    void loginAndPlaceOrder() {
        try {
            AddCartRequestModel model = new AddCartRequestModel();
            model.setQuantity(3);
            model.setSkuId("14121812860");
            model.setItemId("2199811436");
            List<AddCartRequestModel> list = Collections.singletonList(model);
            LoginRequest loginRequest = LoginRequest.builder().account("").password("").build();
            lazadaUIBot.loginAndPlaceOrder(loginRequest, list);
        } catch (Exception ex) {
            lazadaUIBot.get_driver().quit();
        } finally {
            lazadaUIBot.get_driver().quit();
        }
    }

    @Test
    void testGetAllCartItems() {
        String content = "SELECT ALL (1 ITEM(S))";
        int itemsCount = Integer.parseInt(content.split("\\(")[1].split(" ")[0]);
        System.out.println(itemsCount);

        String priceContent = "$0.70";
        double totalPrice = Double.parseDouble(priceContent.substring(1));
        System.out.println(totalPrice);
    }

    @Test
    void testParseFiddlerHeaders() throws InterruptedException {
//        LazadaUIBot lazadaUIBot = new LazadaUIBot();
        lazadaUIBot.setSavedHeaders(SeleniumHelper.parseFiddlerSavedHeaders("Key: Host; Value: member.lazada.sg\n" +
                "Key: Connection; Value: keep-alive\n" +
                "Key: sec-ch-ua; Value: \"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"\n" +
                "Key: x-umidtoken; Value: defaultToken2_load_failed with timeout@@https://checkout.lazada.sg/shipping?spm=a2o42.cart.proceed_to_checkout.proceed_to_checkout@@1676298269704\n" +
                "Key: X-CSRF-TOKEN; Value: 34be0e13a3e36\n" +
                "Key: sec-ch-ua-mobile; Value: ?0\n" +
                "Key: User-Agent; Value: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36\n" +
                "Key: Accept; Value: application/json, text/plain, */*\n" +
                "Key: X-Requested-With; Value: XMLHttpRequest\n" +
                "Key: x-ua; Value: defaultUAFromHeader with @@https://checkout.lazada.sg/shipping?spm=a2o42.cart.proceed_to_checkout.proceed_to_checkout@@1676298264749\n" +
                "Key: sec-ch-ua-platform; Value: \"macOS\"\n" +
                "Key: Origin; Value: https://checkout.lazada.sg\n" +
                "Key: Sec-Fetch-Site; Value: same-site\n" +
                "Key: Sec-Fetch-Mode; Value: cors\n" +
                "Key: Sec-Fetch-Dest; Value: empty\n" +
                "Key: Referer; Value: https://checkout.lazada.sg/\n" +
                "Key: Accept-Encoding; Value: gzip, deflate, br\n" +
                "Key: Accept-Language; Value: zh-CN,zh;q=0.9,en;q=0.8\n" +
                "Key: Cookie; Value: client_type=desktop; client_type=desktop; lzd_cid=028f8133-bfc9-4e09-bb54-c3354b224323; t_uid=028f8133-bfc9-4e09-bb54-c3354b224323; hng=SG|en-SG|SGD|702; t_fv=1675077397877; _gcl_au=1.1.1704313308.1675077399; anon_uid=8be5b39579653c327de7be1ba568899e; _tb_token_=34be0e13a3e36; _fbp=fb.1.1675077409531.1992789944; AMCVS_126E248D54200F960A4C98C6%40AdobeOrg=1; _bl_uid=61ls7dCtiR9pg5uk8l3atzd0ymvt; G_ENABLED_IDPS=google; cna=oxNbG8k4sh0CAXd6cVpBr7yT; _ga=GA1.2.158551117.1675258373; lzd_sid=1b67a6967968e678904d2bee91c7b07d; lzd_uid=1175216090; lzd_uti=%7B%22fpd%22%3A%229999-99-99%22%2C%22lpd%22%3A%229999-99-99%22%2C%22cnt%22%3A%220%22%7D; cto_axid=F4hIN-4FIcOkI6YgfMmoSbbux4FUoRDm; AMCV_126E248D54200F960A4C98C6%40AdobeOrg=-1124106680%7CMCIDTS%7C19401%7CMCMID%7C68141545177722493671089789448770007338%7CMCAAMLH-1676778357%7C9%7CMCAAMB-1676778357%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1676180757s%7CNONE%7CvVersion%7C5.2.0; xlly_s=1; client_type=desktop; __wpkreporterwid_=f3b2f3f0-424c-461f-369c-2d2a5fa165a3; t_sid=RcyUjegSOv3TnxWZ4WjtFIcVfsdwWe9i; utm_channel=NA; _m_h5_tk=fc7e7f0eeb9a8a8cf1aa9649ef782f4e_1676308623899; _m_h5_tk_enc=c235faafe244af3af7fa98b7807e211e; lzd_b_csg=a5275b6d; sgcookie=E10017MLVxp1pJopZvzJWrwt9Vc3eo%2FVdEzmdGg2VavZ90X%2FSXaKWxGHl5Cc1lROk%2F1ivz8bjoXIzuBYP0UyYlXrKrLJReQtgmrG8%2FnIkal2KnY%3D; cto_bundle=sXeYI18lMkZQd1klMkIwNmJzZDZJVlYzdG0wTDBzdFZMYk4wTmpFZ01JMElIY1FFSGhpbkdsSnl3aiUyRiUyQjZPcDRESDdyY3pjNWFXQzJrMlRVY3VLU21SN1FzZVd2YUhWRm9qNlg0S09VV0NadndoN25kR1V4NVMyOFQ4VjJ5a0xvQkZUJTJGcHNlZFRKWjJQRjdnZ2RZd0luQWQ1d25DQkJwamNGUEZRJTJCOUJ0UTQ1N1ZjcnU4WDg3JTJGZTZKayUyQjhMZ1ZEaTBWMEd0NGI2; _uetsid=c4271a50ab7811eda750e33c59abd91c; _uetvid=96aa6710a08f11ed9249e3e56ee8a496; tfstk=cTJGBSaf9waSRyMvRF6sEdtaYndGaT2VrKJBLDOAK-vfrlpCbsXLT7SR_DbiPVPf.; l=fBrRFcUcTWfNjri8BO5Znurza77tEQRf1sPzaNbMiIEGa6_FwFiI-NCebiRXkdtjgTf0heKy58T6YdHJPO4NwwuKaEm_n2o2DY9w-4m1Skf..; isg=BIiIbXdCf0E2yZP4VTa2Ah0DWfCaMew7SjkWckI7HIP2HSuH60XXy2NblfVtLaQT"));
        try {
            CreateAddressRequestModel address = new CreateAddressRequestModel();
            address.setName("Stephen");
            address.setPhone("81542599");
            address.setPostCode("238800");
            address.setDetailAddress("MenPaiHao 30");
            address.setExtendAddress("Specific Info");
            address.setLoading(false);
            lazadaUIBot.updateSingleAddress(address);
        } catch (Exception ex) {
            lazadaUIBot.get_driver().quit();
        } finally {
            lazadaUIBot.get_driver().quit();
        }
    }

    @Test
    void getAddrByPostCode() {
//        LazadaUIBot lazadaUIBot = new LazadaUIBot();
        lazadaUIBot.setSavedHeaders(SeleniumHelper.parseFiddlerSavedHeaders("Key: Host; Value: member.lazada.sg\n" +
                "Key: Connection; Value: keep-alive\n" +
                "Key: sec-ch-ua; Value: \"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"\n" +
                "Key: x-umidtoken; Value: defaultToken2_load_failed with timeout@@https://checkout.lazada.sg/shipping?spm=a2o42.cart.proceed_to_checkout.proceed_to_checkout@@1676298269704\n" +
                "Key: X-CSRF-TOKEN; Value: 34be0e13a3e36\n" +
                "Key: sec-ch-ua-mobile; Value: ?0\n" +
                "Key: User-Agent; Value: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36\n" +
                "Key: Accept; Value: application/json, text/plain, */*\n" +
                "Key: X-Requested-With; Value: XMLHttpRequest\n" +
                "Key: x-ua; Value: defaultUAFromHeader with @@https://checkout.lazada.sg/shipping?spm=a2o42.cart.proceed_to_checkout.proceed_to_checkout@@1676298264749\n" +
                "Key: sec-ch-ua-platform; Value: \"macOS\"\n" +
                "Key: Origin; Value: https://checkout.lazada.sg\n" +
                "Key: Sec-Fetch-Site; Value: same-site\n" +
                "Key: Sec-Fetch-Mode; Value: cors\n" +
                "Key: Sec-Fetch-Dest; Value: empty\n" +
                "Key: Referer; Value: https://checkout.lazada.sg/\n" +
                "Key: Accept-Encoding; Value: gzip, deflate, br\n" +
                "Key: Accept-Language; Value: zh-CN,zh;q=0.9,en;q=0.8\n" +
                "Key: Cookie; Value: client_type=desktop; client_type=desktop; lzd_cid=028f8133-bfc9-4e09-bb54-c3354b224323; t_uid=028f8133-bfc9-4e09-bb54-c3354b224323; hng=SG|en-SG|SGD|702; t_fv=1675077397877; _gcl_au=1.1.1704313308.1675077399; anon_uid=8be5b39579653c327de7be1ba568899e; _tb_token_=34be0e13a3e36; _fbp=fb.1.1675077409531.1992789944; AMCVS_126E248D54200F960A4C98C6%40AdobeOrg=1; _bl_uid=61ls7dCtiR9pg5uk8l3atzd0ymvt; G_ENABLED_IDPS=google; cna=oxNbG8k4sh0CAXd6cVpBr7yT; _ga=GA1.2.158551117.1675258373; lzd_sid=1b67a6967968e678904d2bee91c7b07d; lzd_uid=1175216090; lzd_uti=%7B%22fpd%22%3A%229999-99-99%22%2C%22lpd%22%3A%229999-99-99%22%2C%22cnt%22%3A%220%22%7D; cto_axid=F4hIN-4FIcOkI6YgfMmoSbbux4FUoRDm; AMCV_126E248D54200F960A4C98C6%40AdobeOrg=-1124106680%7CMCIDTS%7C19401%7CMCMID%7C68141545177722493671089789448770007338%7CMCAAMLH-1676778357%7C9%7CMCAAMB-1676778357%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1676180757s%7CNONE%7CvVersion%7C5.2.0; xlly_s=1; client_type=desktop; __wpkreporterwid_=f3b2f3f0-424c-461f-369c-2d2a5fa165a3; t_sid=RcyUjegSOv3TnxWZ4WjtFIcVfsdwWe9i; utm_channel=NA; _m_h5_tk=fc7e7f0eeb9a8a8cf1aa9649ef782f4e_1676308623899; _m_h5_tk_enc=c235faafe244af3af7fa98b7807e211e; lzd_b_csg=a5275b6d; sgcookie=E10017MLVxp1pJopZvzJWrwt9Vc3eo%2FVdEzmdGg2VavZ90X%2FSXaKWxGHl5Cc1lROk%2F1ivz8bjoXIzuBYP0UyYlXrKrLJReQtgmrG8%2FnIkal2KnY%3D; cto_bundle=sXeYI18lMkZQd1klMkIwNmJzZDZJVlYzdG0wTDBzdFZMYk4wTmpFZ01JMElIY1FFSGhpbkdsSnl3aiUyRiUyQjZPcDRESDdyY3pjNWFXQzJrMlRVY3VLU21SN1FzZVd2YUhWRm9qNlg0S09VV0NadndoN25kR1V4NVMyOFQ4VjJ5a0xvQkZUJTJGcHNlZFRKWjJQRjdnZ2RZd0luQWQ1d25DQkJwamNGUEZRJTJCOUJ0UTQ1N1ZjcnU4WDg3JTJGZTZKayUyQjhMZ1ZEaTBWMEd0NGI2; _uetsid=c4271a50ab7811eda750e33c59abd91c; _uetvid=96aa6710a08f11ed9249e3e56ee8a496; tfstk=cTJGBSaf9waSRyMvRF6sEdtaYndGaT2VrKJBLDOAK-vfrlpCbsXLT7SR_DbiPVPf.; l=fBrRFcUcTWfNjri8BO5Znurza77tEQRf1sPzaNbMiIEGa6_FwFiI-NCebiRXkdtjgTf0heKy58T6YdHJPO4NwwuKaEm_n2o2DY9w-4m1Skf..; isg=BIiIbXdCf0E2yZP4VTa2Ah0DWfCaMew7SjkWckI7HIP2HSuH60XXy2NblfVtLaQT"));
//        LazadaUrls urls = new LazadaUrls();
//        urls.listAddressApi = "https://member.lazada.sg/address/api/listAddress";
//        urls.getByPostCodeApi = "https://member.lazada.sg/locationtree/api/getByPostCode";
//        lazadaUIBot.setUrls(urls);
        try {
            lazadaUIBot.getAddrByPostCode("238801");
        } catch (Exception ex) {
            lazadaUIBot.get_driver().quit();
        } finally {
            lazadaUIBot.get_driver().quit();
        }
    }

    @Test
    void getEmailVerifyCode() throws MessagingException, IOException {
        EmailService service = lazadaUIBot.getEmailService();
        service.getEmailVerifyCode("willowwu123@gmail.com", "nvqwbcmkgcsmlcog");
    }
}