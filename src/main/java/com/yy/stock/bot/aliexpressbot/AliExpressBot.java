package com.yy.stock.bot.aliexpressbot;

import com.google.common.collect.Lists;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressUrls;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressXpaths;
import com.yy.stock.bot.helper.SeleniumHelper;
import com.yy.stock.bot.lazadaui.model.address.*;
import com.yy.stock.bot.lazadaui.model.cart.AddCartRequestModel;
import com.yy.stock.bot.lazadaui.model.cart.AddCartRespModule;
import com.yy.stock.bot.lazadaui.model.cart.CartApiResponseModel;
import com.yy.stock.bot.lazadaui.model.cart.CartCountRspModel;
import com.yy.stock.common.email.EmailService;
import lombok.Data;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;

@Data
@Component
public class AliExpressBot {
    private final AliExpressXpaths xpaths;
    private final AliExpressUrls urls;
    private ChromeDriver _driver;
    private RestTemplate restTemplate;
    private HttpHeaders savedHeaders;
    private EmailService emailService;

    //    @Autowired
    public AliExpressBot(AliExpressXpaths xpaths, AliExpressUrls urls, EmailService emailService) {
        System.out.println("Construct AliExpressBot Instance...");
        this.xpaths = xpaths;
        this.urls = urls;
        this.emailService = emailService;
//        this.restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        this.restTemplate = new RestTemplate(factory);

        initHeaders();
    }

    private ChromeDriver getDriver() {
        if (this._driver == null) {
            this._driver = initChromeDriver();
        }
        return this._driver;
    }

    public GetByPostCodeRespModel getAddrByPostCode(String postCode) {
        HttpEntity<String> entity = new HttpEntity<>(savedHeaders);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("postCode", postCode);
        HttpEntity<AddressApiResponseModel<GetByPostCodeRespModel>> response = restTemplate.exchange(
                urls.getByPostCodeApi + "?postCode={postCode}",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                queryParams);
        System.out.println(response);
        return response.getBody().getModule();
    }

    public boolean validatePhone(String phone) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("phone", phone);
        paramMap.add("type", "ADDRESS");
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, savedHeaders);

        HttpEntity<AddressApiResponseModel<ValidatePhoneRespModel>> response = restTemplate.exchange(
                urls.validatePhoneApi,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<AddressApiResponseModel<ValidatePhoneRespModel>>() {
                }
        );
        System.out.println(response);
        return response.getBody().getModule().isValid();
    }

    public long createAddress(CreateAddressRequestModel address) {
        HttpEntity<CreateAddressRequestModel> entity = new HttpEntity<>(address, savedHeaders);

        HttpEntity<AddressApiResponseModel<CreateAddressRespModel>> response = restTemplate.exchange(
                urls.createAddressApi,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        System.out.println(response);
        return response.getBody().getModule().getAddressId();
    }

    public ChromeDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver",
                "/Users/minmin/Documents/Fadacai88888/wimoor-main/yy-stock/stock/libs/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.addArguments("--disable-popup-blocking"); // 禁用阻止弹出窗口
        options.addArguments("no-sandbox");//禁用沙盒
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("disable-extensions"); // 禁用扩展
        options.addArguments("no-default-browser-check"); // 默认浏览器检查

        List<String> excludeSwitches = Lists.newArrayList("enable-automation");//设置ExperimentalOption
        options.setExperimentalOption("excludeSwitches", excludeSwitches);
//        options.setExperimentalOption("useAutomationExtension", false);
        Map<String, Object> prefs = new HashMap();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);// 禁用保存密码提示框

        // set performance logger
        // this sends Network.enable to chromedriver
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        ChromeDriver driver = new ChromeDriver(options);

        //修改window.navigator.webdirver=undefined，防机器人识别机制
        Map<String, Object> command = new HashMap<>();
        command.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", command);
        return driver;
    }

    public boolean loginAndPlaceOrder(List<AddCartRequestModel> addCartRequestModels) throws InterruptedException {
        login();
        SeleniumHelper.updateHeaderValueFromLogs(getDriver(), savedHeaders, new String[]{"x-csrf-token", "x-umidtoken", "x-ua"});
        updateCookies(getDriver().manage().getCookies());
        cartAdd(addCartRequestModels);
        CreateAddressRequestModel address = new CreateAddressRequestModel();
        updateSingleAddress(address);
        checkout();
        placeOrder();
        getDriver().quit();
        return true;
    }

    public long updateSingleAddress(CreateAddressRequestModel address) throws InterruptedException {
        List<ListAddressRespModel> list = listAddress();
        System.out.println(list);
        for (ListAddressRespModel addr : list) {
            if (!deleteAddress(addr.getId())) {
                throw new InterruptedException();
            }
            Thread.sleep(2000);
        }
        GetByPostCodeRespModel locationInfo = getAddrByPostCode(address.getPostCode());
        address.setLocationTreeAddressName(locationInfo.getLocationTreeAddressName());
        address.setLocationTreeAddressId(locationInfo.getLocationTreeAddressId());

        long newAddressId = createAddress(address);
        Thread.sleep(2000);
        return newAddressId;
    }

    public String trackLogistic(String fulfillmentOrderId, String fulfillmentOrderPackageId) {
        HttpEntity<String> entity = new HttpEntity<>(savedHeaders);
        HttpEntity<String> response = restTemplate.exchange(
                urls.logisticViewPage + "?fulfillmentOrderId=" + fulfillmentOrderId + "&fulfillmentOrderPackageId=" + fulfillmentOrderPackageId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                });
        String html = response.getBody();
        // todo
        return html;
    }

    public List<ListAddressRespModel> listAddress() {
        updateHeaders("Accept-Encoding", "gzip, deflate, br");
        HttpEntity<String> entity = new HttpEntity<>(savedHeaders);
        try {
            HttpEntity<AddressApiResponseModel<List<ListAddressRespModel>>> response = restTemplate.exchange(urls.listAddressApi, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            return response.getBody().getModule();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean deleteAddress(long addressId) {
        String paramMap = MessageFormat.format("{\"addressId\":\"{0}\"}", addressId);
        HttpEntity<String> entity = new HttpEntity<>(paramMap, savedHeaders);

        HttpEntity<AddressApiResponseModel<Object>> response = restTemplate.exchange(urls.deleteAddressApi, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {
        });
        return response.getBody().getSuccess();
    }

    private void updateCookies(Set<Cookie> cookies) {
//        savedCookie = cookies;
        StringBuilder builder = new StringBuilder();
        for (Cookie cookie : cookies) {
            builder.append(cookie.getName())
                    .append("=")
                    .append(cookie.getValue())
                    .append(";");
        }
        savedHeaders.set("Cookie", builder.toString());
    }

    private void updateHeaders(String key, String value) {
        getSavedHeaders().set(key, value);
    }

    public void login() {

//        getDriver().get("https://www.lazada.sg/");
//        Cookie[] mycookies = new ObjectMapper().readValue(mycookie, Cookie[].class);
//        System.out.println("value==: " + mycookies);
//        for (Cookie cookie : mycookies) {
//            getDriver().manage().addCookie(cookie);
//        }
//        getDriver().get("https://www.lazada.sg/");

        getDriver().get(urls.loginPage);
        try {
            WebElement email_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getAccountInput());
            SeleniumHelper.clearAndType(email_input, "willowwu123@gmail.com");
            Thread.sleep((long) (Math.random() * 3000));

            WebElement password_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getPasswordInput());
            SeleniumHelper.clearAndType(password_input, "Fadacai88888");
            Thread.sleep((long) (Math.random() * 3000));

            WebElement login_button = SeleniumHelper.getByXpath(getDriver(), xpaths.getLoginButton());
            login_button.click();
            Thread.sleep(8888);

            try {
                new WebDriverWait(getDriver(), Duration.ofSeconds(6)).until(ExpectedConditions.urlContains("member.lazada.sg/user/verification-pc"));
            } catch (Exception ex) {

            }

            List<WebElement> verify_buttons = SeleniumHelper.getByClassName(getDriver(), "verify-item");
            WebElement email_verify_button = verify_buttons.stream().filter(b -> b.getText().equals("Email Verification")).findFirst().orElse(null);
//             SeleniumHelper.getByXpath(getDriver(), xpaths.getAccountVerifyButton());
            email_verify_button.click();

            WebElement email_send_button = SeleniumHelper.getByXpath(getDriver(), xpaths.getVerifyEmailSendButton());
            email_send_button.click();

            Thread.sleep(18000);
            String code = emailService.getEmailVerifyCode("willowwu123@gmail.com", "nvqwbcmkgcsmlcog");

            if (code != "") {
                WebElement email_code_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getVerifyEmailCodeInput());
                SeleniumHelper.clearAndType(email_code_input, code);

                Thread.sleep((long) (Math.random() * 3000));

                WebElement verifySubmitButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getVerifySubmitButton());
                verifySubmitButton.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String currentTime = df.format(new Date());
            System.out.println(currentTime);// new Date()为获取当前系统时间
//            String path = "C:\\Users\\Desktop\\pic\\Exception_" + currentTime + ".png";
//            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);  // 调用截图方法
//            FileUtils.copyFile(src, new File(path));
            System.out.println("登录失败!");
            getDriver().quit();
        }
    }

    public void cartAdd(List<AddCartRequestModel> model) {
//        String paramMap = "[{\"itemId\":\"2199811436\",\"skuId\":\"14121812860\",\"quantity\":8}]";
        int count = cartCount();

        // todo
        while (cartCount() > 0) {
            cartClear();
        }

        HttpEntity<List<AddCartRequestModel>> entity = new HttpEntity<>(model, savedHeaders);

        HttpEntity<CartApiResponseModel<AddCartRespModule>> response = restTemplate.exchange(urls.addToCartApi, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {
        });
        System.out.println(response);
    }

    public int cartCount() {
        HttpEntity entity = new HttpEntity<>(savedHeaders);

        HttpEntity<CartApiResponseModel<CartCountRspModel>> response = restTemplate.exchange(urls.cartCountApi, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
        CartCountRspModel model = response.getBody().getModule();
        return model.getCartNum();
    }

    public boolean cartClear() {
        return true;
    }

    public void checkout() {
        getDriver().get(urls.cartPage);
        WebElement selectAllDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getSelectAllCartItemsDiv());

        int itemsCount = Integer.parseInt(selectAllDiv.getText().split("\\(")[1].split(" ")[0]);

        WebElement selectAllButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getSelectAllCartItemsButton());
        selectAllButton.click();

        WebElement orderSummaryPriceDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getSubtotalDiv());
        double subTotalPrice = Double.parseDouble(orderSummaryPriceDiv.getText().substring(1));

        WebElement shippingFeeDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getShippingFeeDiv());
        double shippingFee = Double.parseDouble(shippingFeeDiv.getText().substring(1));

        WebElement totalPriceDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getTotalPriceDiv());
        double totalPrice = Double.parseDouble(totalPriceDiv.getText().substring(1));

        WebElement checkoutButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getCheckoutButton());
        checkoutButton.click();
    }

    public void placeOrder() {
        getDriver().get(urls.placeOrderPage);
    }

    private void initHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "member.lazada.sg");
        headers.add("Connection", "keep-alive");
        headers.add("Content-Length", "85");
        headers.add("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        headers.add("Content-Type", "application/json");
        headers.add("bx-sys", "ua-l:no__um-l:lazada__js:https://laz-g-cdn.alicdn.com/");
        headers.add("Accept", "application/json, text/plain, */*");
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.add("bx-v", "2.2.3");
        headers.add("sec-ch-ua-platform", "\"macOS\"");
        headers.add("Origin", "https://member.lazada.sg");
        headers.add("Sec-Fetch-Site", "same-origin");
        headers.add("Sec-Fetch-Mode", "cors");
        headers.add("Sec-Fetch-Dest", "empty");
        headers.add("Referer", "https://member.lazada.sg/user/login?spm=a2o42.home.header.d5.100546b5LfhQvN&redirect=https%3A%2F%2Fwww.lazada.sg%2F%23hp-flash-sale");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9");
        this.savedHeaders = headers;
    }
}
