package com.yy.stock.bot.aliexpressbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yy.stock.bot.Bot;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressUrls;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressXpaths;
import com.yy.stock.bot.base.LoginRequest;
import com.yy.stock.bot.base.Product;
import com.yy.stock.bot.base.ShipmentInfo;
import com.yy.stock.bot.helper.SeleniumHelper;
import com.yy.stock.bot.lazadaui.model.address.*;
import com.yy.stock.bot.lazadaui.model.cart.AddCartRequestModel;
import com.yy.stock.bot.lazadaui.model.cart.AddCartRespModule;
import com.yy.stock.bot.lazadaui.model.cart.CartApiResponseModel;
import com.yy.stock.bot.lazadaui.model.cart.CartCountRspModel;
import com.yy.stock.common.email.EmailService;
import com.yy.stock.common.exception.OverTopShipFeeException;
import com.yy.stock.common.exception.SupplierUnavailableException;
import com.yy.stock.common.exception.WrongCartItemsCountAddedException;
import com.yy.stock.common.exception.WrongStockPriceException;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.StockStatusService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
@Slf4j
public class AliExpressBot implements Bot {
    private final AliExpressXpaths xpaths;
    private final AliExpressUrls urls;
    public LoginRequest loginRequest;
    private ChromeDriver _driver;
    private RestTemplate restTemplate;
    private HttpHeaders savedHeaders;
    private EmailService emailService;
    private StockRequest request;
    private BuyerAccountService buyerAccountService;
    //    private SupplierService supplierService;
    private StockStatusService stockStatusService;

    public AliExpressBot(AliExpressXpaths xpaths, AliExpressUrls urls, EmailService emailService,
//                       SupplierService supplierService,
                         StockStatusService stockStatusService,
                         BuyerAccountService buyerAccountService) {
        System.out.println("Construct LazadaUIBot Instance...");
        this.xpaths = xpaths;
        this.urls = urls;
        this.emailService = emailService;
//        this.restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        this.restTemplate = new RestTemplate(factory);

        initHeaders();
//        this.supplierService = supplierService;
        this.stockStatusService = stockStatusService;
        this.buyerAccountService = buyerAccountService;
    }

    public LoginRequest generLoginRequest() {
        LoginRequest loginRequest = LoginRequest.builder()
                .account(request.getBuyerAccount().getEmail())
                .password(request.getBuyerAccount().getPassword())
                .build();
        return loginRequest;
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

    public boolean loginAndPlaceOrder() throws InterruptedException, OverTopShipFeeException, JsonProcessingException {
        LoginRequest loginRequest = generLoginRequest();
        login(loginRequest);

        SeleniumHelper.updateHeaderValueFromLogs(getDriver(), savedHeaders, new String[]{"x-csrf-token", "x-umidtoken", "x-ua"});
        updateCookies(getDriver().manage().getCookies());

//        cartAdd(generAddCartRequest());

        updateSingleAddress(generCreateAddressRequest());
        buyNow();
        checkout();

        placeOrder();

        saveTrackInfo();

        getDriver().quit();
        return true;
    }

    private void saveTrackInfo() {
        String platformOrderId = getPlatformOrderId();
        String shipTrackNumber = getShipTrackNumber();
        var status = request.getStockStatus();
        status.setPlatformOrderId(platformOrderId);
        if (shipTrackNumber != null) {
            status.setShipmentTrackNumber(shipTrackNumber);
        }
        stockStatusService.save(status);
    }

    private String getShipTrackNumber() {
        return null;
    }

    private String getPlatformOrderId() {
        return null;
    }

    public CreateAddressRequestModel generCreateAddressRequest() {
        CreateAddressRequestModel model = new CreateAddressRequestModel();
        model.setName(request.getAddress().getName());
        model.setPhone(request.getAddress().getPhone());
        model.setPostCode(request.getAddress().getPostalCode());
        model.setDetailAddress(request.getAddress().getAddressLine1());
        model.setExtendAddress(request.getAddress().getAddressLine2());
        model.setLoading(false);
        return model;
    }

    public void updateSingleAddress(CreateAddressRequestModel address) throws InterruptedException {
        getDriver().get(urls.addressListPage);
        var deleteAddressButton = SeleniumHelper.getByXpath(getDriver(), xpaths.deleteAddressButton);
        deleteAddressButton.click();
        var confirmDeleteAddressButton = SeleniumHelper.getByXpath(getDriver(), xpaths.confirmDeleteAddressButton);
        confirmDeleteAddressButton.click();
        Thread.sleep(6666);
        var addAddressButton = SeleniumHelper.getByXpath(getDriver(), xpaths.addAddressButton);
        addAddressButton.click();
        Thread.sleep(6666);


        var nameInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressNameInput);
        var name = cleanAddressName(request.getAddress().getName());
        if (name.length() > 49) {
            name = name.substring(0, 49);
        }
        nameInput.sendKeys(name);
        Thread.sleep(1000);

        var phoneInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressPhoneInput);
        var phone = cleanAddressPhone(request.getAddress().getPhone());
        phoneInput.sendKeys(phone);
        Thread.sleep(1000);

        var cepInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressCepInput);
        cepInput.sendKeys(request.getAddress().getPostalCode());
        Thread.sleep(1000);
        var cepSelectButton = SeleniumHelper.getByXpath(getDriver(), xpaths.addressCepSelectButton);
        cepSelectButton.click();
        Thread.sleep(3000);

        var bairrorInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressBairrorInput);
        var bairror = request.getAddress().getAddressLine1();
        if (bairror != null && bairror != "") {
            if (bairror.length() > 49) {
                bairror = bairror.substring(bairror.length() - 30);
            }
            bairror = bairror.replace(',', ' ');
            SeleniumHelper.clearAndType(bairrorInput, bairror);
        } else {
            var autoText = bairrorInput.getAttribute("value");
            if (autoText == "") {
                SeleniumHelper.clearAndType(bairrorInput, "Casa");
            }
        }
        Thread.sleep(1000);

        var ruaInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressRuaInput);
        String rua = cleanAddressRua(request.getAddress().getAddressLine2());
        var ruaWords = rua.split(" ");
        var ruaNum = ruaWords[ruaWords.length - 1];
        if (rua.length() > 1) {
            if (SeleniumHelper.isNumberString(ruaNum)) {
                ruaWords = Arrays.copyOf(ruaWords, ruaWords.length - 1);
            } else {
                ruaNum = "0";
            }
            rua = String.join(" ", ruaWords);
        }
        SeleniumHelper.clearAndType(ruaInput, rua);
        Thread.sleep(1000);
        var ruaNumInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressRuaNumInput);
        SeleniumHelper.clearAndType(ruaNumInput, ruaNum);
        Thread.sleep(1000);

        var noteInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressNoteInput);
        var note = request.getAddress().getAddressLine3();
        if (note != null && note != "") {
            note = note.replace(',', ' ');
        }
        SeleniumHelper.clearAndType(noteInput, note);
        Thread.sleep(1000);

        var cpfInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressCpfInput);
        // 使用此字段存储巴西cpf
        var cpf = request.getAddress().getMunicipality();
        cpf = cleanAddressCpf(cpf);
        SeleniumHelper.clearAndType(cpfInput, cpf);
        Thread.sleep(1000);

        var setDefaultButton = SeleniumHelper.getByXpath(getDriver(), xpaths.addressSetDefaultButton);
        setDefaultButton.sendKeys(Keys.SPACE);
        Thread.sleep(1000);

        var saveButton = SeleniumHelper.getByXpath(getDriver(), xpaths.addressSaveButton);
        setDefaultButton.click();
        Thread.sleep(1000);

        var firstAddressNameDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.addressFirstAddressNameDiv);
        var peopleFirstName = name.split(" ")[0];
        while (!firstAddressNameDiv.getText().contains(peopleFirstName)) {
            firstAddressNameDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.addressFirstAddressNameDiv);
        }
        Thread.sleep(1000);
    }

    private String cleanAddressCpf(String cpf) {
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(cpf);
        cpf = matcher.replaceAll("");
        return cpf;
    }

    private String cleanAddressRua(String rua) {
        String regEx = "[%&',;=?$\\x22]+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(rua);
        rua = matcher.replaceAll("");
        if (rua.length() > 49) {
            rua = rua.substring(rua.length() - 49);
        }
        return rua;
    }


    public String cleanAddressName(String name) {
        String regEx = "[%&',;=?$\\x22]+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(name);
        return matcher.replaceAll("");
    }

    public String cleanAddressPhone(String phone) {
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phone);
        phone = matcher.replaceAll("");

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
        return phone;
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

    private void updateCookies(Set<Cookie> cookies) throws JsonProcessingException {
//        savedCookie = cookies;
        StringBuilder builder = new StringBuilder();
        for (Cookie cookie : cookies) {
            builder.append(cookie.getName())
                    .append("=")
                    .append(cookie.getValue())
                    .append(";");
        }
        savedHeaders.set("Cookie", builder.toString());
        saveCookies(cookies);
    }

    private void saveCookies(Set<Cookie> cookies) throws JsonProcessingException {
        var cookiesStr = new ObjectMapper().writeValueAsString(cookies);
        var buyerAccount = request.getBuyerAccount();
        buyerAccount.setLoginCookie(cookiesStr);
        buyerAccountService.save(buyerAccount);
    }

    private void updateHeaders(String key, String value) {
        getSavedHeaders().set(key, value);
    }

    public boolean login(LoginRequest loginRequest) {
        if (loginWithCookie(loginRequest)) {
            return true;
        }

        getDriver().get(urls.homePage);

        try {
            WebElement loginTopButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getLoginTopButton());
            loginTopButton.click();
            Thread.sleep((long) (Math.random() * 3000));

            while (getDriver().getCurrentUrl().equals(urls.homePage)) {// 不断的获取地址判断一下，地址有没有变
                // 页面没有跳转就让他等待，等待自己重定向到登录后的页面，然后再获取cookie时就是正确的cookie
            }

            final String loginOrVerifyUrl = getDriver().getCurrentUrl();// 获取跳转后的url地址

//            new WebDriverWait(getDriver(), Duration.ofSeconds(6)).until(ExpectedConditions.urlContains("member.lazada.sg/user/verification-pc"));
            if (loginOrVerifyUrl.contains("member.lazada.sg/user/verification-pc")) {
                verifyLoginByEmail(loginRequest);
            } else {
                inputAccountAndPassword(loginRequest);
                while (!getDriver().getCurrentUrl().equals(urls.homePage)) {// 不断的获取地址判断一下，地址有没有变
                    // 页面没有跳转就让他等待，等待自己重定向到登录后的页面，然后再获取cookie时就是正确的cookie
                }
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
        return true;
    }

    private void verifyLoginByEmail(LoginRequest loginRequest) throws InterruptedException, MessagingException, IOException {
        inputAccountAndPassword(loginRequest);

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

        String accountVerifyEmailAddress = request.getBuyerAccount().getVerifyEmail();
        String accountVerifyEmailPassword = request.getBuyerAccount().getVerifyEmailPassword();
        String code = emailService.getEmailVerifyCode(accountVerifyEmailAddress, accountVerifyEmailPassword);

        if (code != "") {
            WebElement email_code_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getVerifyEmailCodeInput());
            SeleniumHelper.clearAndType(email_code_input, code);

            Thread.sleep((long) (Math.random() * 3000));

            WebElement verifySubmitButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getVerifySubmitButton());
            verifySubmitButton.click();
        }
    }

    private void inputAccountAndPassword(LoginRequest loginRequest) throws InterruptedException {
        WebElement email_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getAccountInput());
        SeleniumHelper.clearAndType(email_input, loginRequest.getAccount());
        Thread.sleep((long) (Math.random() * 3000));

        WebElement password_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getPasswordInput());
        SeleniumHelper.clearAndType(password_input, loginRequest.getPassword());
        Thread.sleep((long) (Math.random() * 3000));

        WebElement login_button = SeleniumHelper.getByXpath(getDriver(), xpaths.getLoginButton());
        login_button.click();
        Thread.sleep(8888);
    }

    public boolean loginWithCookie(LoginRequest loginRequest) {
        getDriver().get(urls.homePage);
        String cookiesStr = loginRequest.getCookies();
        Cookie[] mycookies;
        try {
            mycookies = new ObjectMapper().readValue(cookiesStr, Cookie[].class);
            log.debug("value==: " + mycookies);
            for (Cookie cookie : mycookies) {
                getDriver().manage().addCookie(cookie);
            }
            getDriver().get(urls.homePage);

            WebElement accountTopButtonSpan = SeleniumHelper.getByXpath(getDriver(), xpaths.getAccountTopButtonSpan());
            String spanText = accountTopButtonSpan.getText();
            if (spanText.contains("ACCOUNT")) {
                return true;
            } else {
                return false;
            }
        } catch (JsonProcessingException e) {
            log.debug("存储的cookie string解析失败，请检查！" + loginRequest);
            return false;
        } catch (TimeoutException e) {
            log.debug("selenium超时错误,将重试...", e);
            return false;
        }
    }

    /**
     * @param request
     * @return
     */
    @Override
    public boolean doStock(StockRequest request) throws InterruptedException, JsonProcessingException {
        this.request = request;
        log.info("bot checking available...");

        var supplier = request.getSupplier();
        if (!supplier.getAvailable()) {
            throw new SupplierUnavailableException();
        }


        log.info("bot start to do stock...");
        loginAndPlaceOrder();

        return false;
    }

    private List<AddCartRequestModel> generAddCartRequest() {
        var supplier = request.getSupplier();
        AddCartRequestModel addCartRequestModel = new AddCartRequestModel();
        addCartRequestModel.setItemId(supplier.getApiItemId());
        addCartRequestModel.setSkuId(supplier.getApiSkuId());
        addCartRequestModel.setQuantity(request.getOrderInfo().getQuantity());
        List<AddCartRequestModel> addCartRequest = Collections.singletonList(addCartRequestModel);
        return addCartRequest;
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public ShipmentInfo trackOrder(String orderId) {
        return null;
    }

    /**
     * @param product
     * @return
     */
    @Override
    public boolean returnOrder(Product product) {
        return false;
    }

    public void cartAdd(List<AddCartRequestModel> model) {
//        String paramMap = "[{\"itemId\":\"2199811436\",\"skuId\":\"14121812860\",\"quantity\":8}]";

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
        getDriver().get(urls.cartPage);

        WebElement selectAllButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getSelectAllCartItemsButton());
        selectAllButton.click();

        // 等待全选框被选中
        new WebDriverWait(getDriver(), Duration.ofSeconds(6))
                .until(ExpectedConditions.elementSelectionStateToBe(selectAllButton, true));

        WebElement delteButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getDeleteAllCartItemsButton());
        delteButton.click();

        WebElement confirmDeleteButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getDeleteCartItemsConfirmButton());
        confirmDeleteButton.click();

        WebElement emptyTips = SeleniumHelper.getByXpath(getDriver(), xpaths.getCartEmptyTipsDiv());
        if (emptyTips.getText().contains("Your cart is empty")) {
            return true;
        }
        return false;
    }

    public void buyNow() throws InterruptedException {
        var productPage = request.getSupplier().getUrl();
        getDriver().get(productPage);

        var style1SelectorXpath = request.getSupplier().getUiStyle1Xpath();
        WebElement style1Selector = SeleniumHelper.getByXpath(getDriver(), style1SelectorXpath);
        style1Selector.click();
        Thread.sleep(2333);

        var style2SelectorXpath = request.getSupplier().getUiStyle2Xpath();
        if (style2SelectorXpath != null && style2SelectorXpath != "") {
            WebElement style2Selector = SeleniumHelper.getByXpath(getDriver(), style2SelectorXpath);
            style2Selector.click();
        }
        Thread.sleep(2333);

        var style3SelectorXpath = request.getSupplier().getUiStyle3Xpath();
        if (style3SelectorXpath != null && style3SelectorXpath != "") {
            WebElement style3Selector = SeleniumHelper.getByXpath(getDriver(), style3SelectorXpath);
            style3Selector.click();
        }
        Thread.sleep(2333);

        var amountSelectorXpath = request.getSupplier().getUiAmountXpath();
        WebElement amountSelector = SeleniumHelper.getByXpath(getDriver(), amountSelectorXpath);
        SeleniumHelper.clearAndType(amountSelector, request.getOrderInfo().getQuantity().toString());
        Thread.sleep(2333);

        var buyNowButton = SeleniumHelper.getByXpath(getDriver(), xpaths.buyNowButton);
        buyNowButton.click();
        Thread.sleep(2333);


    }

    public void checkout() throws
            WrongCartItemsCountAddedException, WrongStockPriceException, OverTopShipFeeException {

        while (!getDriver().getCurrentUrl().contains(urls.confirmPaymentPage)) {
            log.info("等待进入confirm页面...");
        }

        int quantityToBuy = request.getOrderInfo().getQuantity();

        WebElement orderSummaryPriceDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getSubtotalDiv());
        var text = orderSummaryPriceDiv.getText();
        double subTotalPrice = parseBrazilMoney(text);

        if (subTotalPrice > request.getSupplier().getPrice() * quantityToBuy) {
            throw new WrongStockPriceException("we got sub total price without ship fee:" + subTotalPrice);
        }

        WebElement shippingFeeDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getShippingFeeDiv());
        text = shippingFeeDiv.getText();
        double shippingFee = parseBrazilMoney(text);
        if (shippingFee > request.getSupplier().getMaxShipFee()) {
            throw new OverTopShipFeeException("we got a ship fee:" + shippingFee);
        }

        WebElement totalPriceDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getTotalPriceDiv());
        double totalPrice = parseBrazilMoney(totalPriceDiv.getText());

        WebElement payNowButton = SeleniumHelper.getByXpath(getDriver(), xpaths.payNowButton);
        payNowButton.click();
    }

    public double parseBrazilMoney(String text) {
        text = text.substring(3);
        text = text.replace(',', '.');
        return Double.parseDouble(text);
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
