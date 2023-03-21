package com.yy.stock.bot.aliexpressbot;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.bot.BaseBot;
import com.yy.stock.bot.aliexpressbot.model.logistic.aliexpress.LogisticDetailResponseModel;
import com.yy.stock.bot.aliexpressbot.model.logistic.cainiao.CainiaoGlobalLogisticDetailResponseModel;
import com.yy.stock.bot.aliexpressbot.model.logistic.cainiao.Module;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressClassSelectors;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressUrls;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressXpaths;
import com.yy.stock.bot.base.MyCookie;
import com.yy.stock.bot.base.Product;
import com.yy.stock.bot.base.ShipmentInfo;
import com.yy.stock.bot.helper.RestTemplateHelper;
import com.yy.stock.bot.helper.SeleniumHelper;
import com.yy.stock.bot.lazadaui.model.address.AddressApiResponseModel;
import com.yy.stock.bot.lazadaui.model.address.ListAddressRespModel;
import com.yy.stock.bot.lazadaui.model.cart.AddCartRequestModel;
import com.yy.stock.bot.lazadaui.model.cart.AddCartRespModule;
import com.yy.stock.bot.lazadaui.model.cart.CartApiResponseModel;
import com.yy.stock.bot.lazadaui.model.cart.CartCountRspModel;
import com.yy.stock.common.email.EmailService;
import com.yy.stock.common.exception.OverTopShipFeeException;
import com.yy.stock.common.exception.PayFailedException;
import com.yy.stock.common.exception.SupplierUnavailableException;
import com.yy.stock.common.exception.WrongStockPriceException;
import com.yy.stock.common.util.YamlSourceFactory;
import com.yy.stock.config.StatusEnum;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.PlatformService;
import com.yy.stock.service.StockStatusService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
@Scope("prototype")
@Slf4j
@PropertySource(value = "classpath:configs/aliexpress.yml", factory = YamlSourceFactory.class)    // 指定自定义配置文件位置和名称
public class AliExpressBot extends BaseBot {
    private final AliExpressXpaths xpaths;
    private final AliExpressUrls urls;
    private final AliExpressClassSelectors classSelectors;
    public BuyerAccount buyerAccount;
    private boolean logined = false;
    private RestTemplate restTemplate;
    private HttpHeaders savedHeaders;
    private EmailService emailService;
    private StockRequest request;
    // 付款开关，打开后才点击付款按钮，否则跳过付款，直接保存状态为已备货
    @Value("${bot.paySwitch}")
    private boolean paySwitch;
    private BuyerAccountService buyerAccountService;
    //    private SupplierService supplierService;
    private PlatformService platformService;
    private StockStatusService stockStatusService;


    private AliExpressBot(AliExpressXpaths xpaths, AliExpressUrls urls, AliExpressClassSelectors classSelectors, EmailService emailService,
//                       SupplierService supplierService,
                          PlatformService platformService,
                          StockStatusService stockStatusService,
                          BuyerAccountService buyerAccountService) {
        System.out.println("Construct LazadaUIBot Instance...");
        this.stockStatusService = stockStatusService;
        this.buyerAccountService = buyerAccountService;
        this.platformService = platformService;
        this.xpaths = xpaths;
        this.urls = urls;
        this.classSelectors = classSelectors;
        this.emailService = emailService;
//        this.restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        this.restTemplate = new RestTemplate(factory);

        initHeaders();
//        this.supplierService = supplierService;
    }

    public boolean loginAndPlaceOrder() throws InterruptedException, OverTopShipFeeException, JsonProcessingException {
        login();

        SeleniumHelper.updateHeaderValueFromLogs(getDriver(), savedHeaders, new String[]{"x-csrf-token", "x-umidtoken", "x-ua"});
        var c = getDriverCookies();
        updateCookiesInHeaders(c);
        saveCookiesToBuyerAccount(c);

        updateSingleAddress();

        clickBuyNow();

        checkout();

        payNow();

        saveOrderId();

        getDriver().quit();
        buyerAccount.setInBuying(false);
        buyerAccountService.save(buyerAccount);
        return true;
    }

    private void saveOrderId() {
        var stockStatus = request.getStockStatus();
        try {
            var platformOrderId = getPlatformOrderId();
            stockStatus.setPlatformOrderId(platformOrderId);
            stockStatusService.save(stockStatus);
        } catch (Exception ex) {
            stockStatus.setLog("付款后保存订单ID时出错!ex:" + ex.getMessage());
            stockStatus.setStatus(StatusEnum.payedButInfoSaveError.name());
            stockStatusService.save(stockStatus);
            log.error("付款后保存订单ID时出错! statusID:" + stockStatus.getId());
        }
    }

    public void trackLogisticByAmazonOrderInfo(OrderItemAdaptorInfoDTO order) throws JsonProcessingException, InterruptedException {
        var stockStatus = stockStatusService.getOrCreateByOrderItemInfo(order);
        trackLogisticByStockStatus(stockStatus);
    }

    public void trackLogisticByStockStatus(StockStatus stockStatus) throws JsonProcessingException, InterruptedException {
        var platformOrderId = stockStatus.getPlatformOrderId();
        if (platformOrderId == null || platformOrderId == "") {
            stockStatus.setLog("状态信息中未保存平台订单ID!");
            stockStatus.setStatus(StatusEnum.payedButInfoSaveError.name());
            stockStatusService.save(stockStatus);
            log.error("状态信息中未保存平台订单ID! statusID:" + stockStatus.getId());
            return;
        }
        var trackNumber = stockStatus.getShipmentTrackNumber();
        if (trackNumber == null || trackNumber == "") {
            log.info("状态信息中未保存订单追踪号, statusID:" + stockStatus.getId());
            boolean fetched = refetchShipmentTrackNumber(stockStatus);
            if (!fetched) {
                return;
            }
        }

        trackNumber = stockStatus.getShipmentTrackNumber();
        var modules = getCainiaoTrackInfo(trackNumber);
        var json = new ObjectMapper().writer().writeValueAsString(modules);
        stockStatus.setShipment(json);
        stockStatusService.save(stockStatus);

    }

    public List<Module> getCainiaoTrackInfo(String trackNumber) {
        var url = urls.cainiaoGlobalTrackApi + trackNumber;
        HttpEntity<String> entity = new HttpEntity<>(getCainiaoGlobalHeaders());

        try {
            HttpEntity<CainiaoGlobalLogisticDetailResponseModel> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            return response.getBody().getModule();
        } catch (Exception ex) {
            log.error("拉取菜鸟物流追踪信息出错! trackNumber:" + trackNumber + "ex:" + ex.getMessage());
        }
        return null;
    }

    public boolean refetchShipmentTrackNumber(StockStatus stockStatus) throws JsonProcessingException, InterruptedException {
        String shipTrackNumber;

        if (!logined) {
            if (!login()) {
                return false;
            }
            var c = getDriverCookies();
            updateCookiesInHeaders(c);
            saveCookiesToBuyerAccount(c);
        }
        HttpEntity<String> entity = new HttpEntity<>(savedHeaders);
        try {
            HttpEntity<LogisticDetailResponseModel> response = restTemplate.exchange(urls.logisticDetailApi, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            shipTrackNumber = response.getBody().getData().getPackages().get(0).getLogisticsNo();
            if (shipTrackNumber != null) {
                stockStatus.setShipmentTrackNumber(shipTrackNumber);
                stockStatusService.save(stockStatus);
                return true;
            }
        } catch (Exception ex) {
            stockStatus.setLog("拉取物流追踪号失败！ex:" + ex.getMessage());
            stockStatusService.save(stockStatus);
            log.error("拉取物流追踪号失败! statusID:" + stockStatus.getId() + "ex:" + ex.getMessage());
            return false;
        }
        return false;
    }

    @NotNull
    private MyCookie[] getDriverCookies() {
        var cookies = getDriver().manage().getCookies();
        List<MyCookie> list = new ArrayList<>();
        MyCookie[] res = new MyCookie[]{};
        for (var ck : cookies) {
            MyCookie c = new MyCookie();
            BeanUtils.copyProperties(ck, c);
            list.add(c);
        }
        return list.toArray(res);
    }

    private String getPlatformOrderId() {
        getDriver().get(urls.orderListPage);
        var firstOrderIdLabel = SeleniumHelper.getByXpath(getDriver(), xpaths.firstOrderIdLabel);
        var text = firstOrderIdLabel.getText();
        var orderId = text.split(":")[1];
        orderId = orderId.split("\n")[0];
        orderId = orderId.strip();
        return orderId;
    }

//    public CreateAddressRequestModel generCreateAddressRequest() {
//        CreateAddressRequestModel model = new CreateAddressRequestModel();
//        model.setName(request.getAddress().getName());
//        model.setPhone(request.getAddress().getPhone());
//        model.setPostCode(request.getAddress().getPostalCode());
//        model.setDetailAddress(request.getAddress().getAddressLine1());
//        model.setExtendAddress(request.getAddress().getAddressLine2());
//        model.setLoading(false);
//        return model;
//    }

    public void updateSingleAddress() throws InterruptedException {
        getDriver().get(urls.addressListPage);
        try {
            var deleteAddressButton = SeleniumHelper.getByXpath(getDriver(), xpaths.deleteAddressButton);
            deleteAddressButton.click();
            Thread.sleep(2333);
            var confirmDeleteAddressButton = SeleniumHelper.getByClassName(getDriver(), "next-dialog-btn");
            confirmDeleteAddressButton.click();
            Thread.sleep(6666);
        } catch (Exception ex) {
            log.info("只有一个地址，无需删除.");
        }
//        var confirmDeleteAddressButton = SeleniumHelper.getByXpath(getDriver(), xpaths.confirmDeleteAddressButton);
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
        var cepSelectButton = SeleniumHelper.getByClassName(getDriver(), "postcode-option-item-content");
        cepSelectButton.click();
        Thread.sleep(3000);

        var bairrorInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressBairrorInput);
        var bairror = request.getAddress().getAddressLine2();
        if (bairror != null && !bairror.equals("")) {
            if (bairror.length() > 49) {
                bairror = bairror.substring(bairror.length() - 30);
            }
            bairror = bairror.replace(',', ' ');
            SeleniumHelper.clearAndType(bairrorInput, bairror);
        } else {
            var autoText = bairrorInput.getAttribute("value");
            if (autoText.equals("")) {
                SeleniumHelper.clearAndType(bairrorInput, "Casa");
            }
        }
        Thread.sleep(1000);

        var ruaInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressRuaInput);
        String rua = cleanAddressRua(request.getAddress().getAddressLine1());
        var ruaWords = rua.split(" ");
        var ruaNum = ruaWords[ruaWords.length - 1];
        if (rua.length() > 1) {
            if (SeleniumHelper.isNumberString(ruaNum)) {
                ruaWords = Arrays.copyOf(ruaWords, ruaWords.length - 1);
            } else {
                ruaNum = "0";
            }
            rua = String.join(" ", ruaWords);
        } else {
            ruaNum = "0";
        }
        if (!rua.equals("")) {
            SeleniumHelper.clearAndType(ruaInput, rua);
            Thread.sleep(1000);
        }
        var ruaNumInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressRuaNumInput);
        SeleniumHelper.clearAndType(ruaNumInput, ruaNum);
        Thread.sleep(1000);

        var noteInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressNoteInput);
        var note = request.getAddress().getAddressType();
        if (note != null && !note.equals("")) {
            note = note.replace(',', ' ');
        }
        SeleniumHelper.clearAndType(noteInput, note);
        Thread.sleep(1000);

        var cpfInput = SeleniumHelper.getByXpath(getDriver(), xpaths.addressCpfInput);
        // 使用此字段存储巴西cpf
        var cpf = request.getAddress().getDistrict();
        cpf = cleanAddressCpf(cpf);
        SeleniumHelper.clearAndType(cpfInput, cpf);
        Thread.sleep(1000);

        var setDefaultButton = SeleniumHelper.getByXpath(getDriver(), xpaths.addressSetDefaultButton);
        setDefaultButton.sendKeys(Keys.SPACE);
        Thread.sleep(1000);

        var saveButton = SeleniumHelper.getByXpath(getDriver(), xpaths.addressSaveButton);
        saveButton.click();
        Thread.sleep(2000);

        var firstAddressNameDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.addressFirstAddressNameDiv);
        var peopleFirstName = name.split(" ")[0];
        while (!firstAddressNameDiv.getText().contains(peopleFirstName)) {
            firstAddressNameDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.addressFirstAddressNameDiv);
            Thread.sleep(1000);
        }
        Thread.sleep(1000);
    }

    public void changeRegion() throws InterruptedException {
        getDriver().get(urls.homePage);
        String code;
        if (request == null) {
            var platform = platformService.getById(getBuyerAccount().getPlatformId());
            code = platform.getCountry();
        } else {
            code = request.getAddress().getCountryCode();
        }
        var countryName = getCountryNameByCountryCode(code);

        Thread.sleep(6000L);
        var regionDiv = new WebDriverWait(getDriver(), Duration.ofSeconds(6)).until(d -> d.findElement(By.id("switcher-info")));
        regionDiv.click();

        Thread.sleep(2000L);
        var checkListButton = SeleniumHelper.getByClassName(getDriver(), classSelectors.regionCheckListButton);

        var ref = new Object() {
            WebElement selectDiv = null;
        };
        while (ref.selectDiv == null) {
            try {
                ref.selectDiv = new WebDriverWait(getDriver(), Duration.ofSeconds(6)).until(d -> checkListButton.findElement(By.className("address-select")));
            } catch (Exception ex) {
                regionDiv.click();
                Thread.sleep(2000L);
            }
        }

        var links = new WebDriverWait(getDriver(), Duration.ofSeconds(6)).until(d -> checkListButton.findElements(By.tagName("a")));
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

        var selectItemLis = new WebDriverWait(getDriver(), Duration.ofSeconds(6)).until(d -> ref.selectDiv.findElements(By.tagName("li")));
        for (var li : selectItemLis) {
            var dataName = li.getAttribute("data-name");
            if (dataName.equals(countryName)) {
                li.click();
                break;
            }
        }
        Thread.sleep(3000L);

//        var lis = SeleniumHelper.listByXpath(getDriver(), xpaths.regionArrayLis);
//        for (var li : lis) {
//            if (li.getAttribute("data-name").equals(countryName)) {
//                li.click();
//                break;
//            }
//        }
//        Thread.sleep(1000L);

        var languageSwitcher = SeleniumHelper.getByClassName(getDriver(), "switcher-language");
        var languageButton = SeleniumHelper.getByRelativeXpath(getDriver(), languageSwitcher, ".//span[@class='select-item']");
        languageButton.click();
        Thread.sleep(1000L);
        var languageLis = SeleniumHelper.listByRelativeXpath(getDriver(), languageSwitcher, ".//a[@class='switcher-item']");
        for (var a : languageLis) {
            var aText = a.getAttribute("textContent");
            if (aText.equals("English")) {
                a.click();
                break;
            }
        }
        Thread.sleep(2000L);


        var currencySwitcher = SeleniumHelper.getByClassName(getDriver(), "switcher-currency");
        var cyButton = SeleniumHelper.getByRelativeXpath(getDriver(), currencySwitcher, ".//span[@class='select-item']");
        cyButton.click();
        Thread.sleep(1000L);
        var cyLis = SeleniumHelper.listByRelativeXpath(getDriver(), currencySwitcher, ".//a");
        for (var a : cyLis) {
            var aText = a.getAttribute("data-currency");
            if (aText != null && aText.equals("USD")) {
                a.click();
                break;
            }
        }
        Thread.sleep(2000L);

        var saveButton = SeleniumHelper.getByXpath(getDriver(), xpaths.regionSaveButton);
        saveButton.click();
        Thread.sleep(2000L);
    }

    private String getCountryNameByCountryCode(String countryCode) {
        switch (countryCode) {
            case "BR":
                return "Brazil";
            default:
                return "";
        }
    }

    private String cleanAddressCpf(String cpf) {
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(cpf);
        cpf = matcher.replaceAll("");
        return cpf;
    }

    private String cleanAddressRua(String rua) {
        if (rua == null) {
            return "";
        }
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
        String regEx = "[%&'.,;=?$\\x22]+";
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

    private void updateCookiesInHeaders(MyCookie[] cookies) throws JsonProcessingException {
//        savedCookie = cookies;
        StringBuilder builder = new StringBuilder();
        for (var cookie : cookies) {
            builder.append(cookie.getName())
                    .append("=")
                    .append(cookie.getValue())
                    .append(";");
        }
        if (builder.isEmpty()) {
            return;
        }
        savedHeaders.set("Cookie", builder.toString());
    }

    private void saveCookiesToBuyerAccount(MyCookie[] cookies) throws JsonProcessingException {
        var cookiesStr = new ObjectMapper().writeValueAsString(cookies);
        var buyerAccount = getBuyerAccount();
        buyerAccount.setLoginCookie(cookiesStr);
        buyerAccountService.save(buyerAccount);
    }

    private void updateHeaders(String key, String value) {
        getSavedHeaders().set(key, value);
    }

    public boolean login() throws InterruptedException, JsonProcessingException {
        if (loginWithCookie()) {
            handleSuccessLogin();
            return true;
        }

        getDriver().get(urls.homePage);

        try {
            if (getDriver().getCurrentUrl().startsWith(urls.homePage)) {
                WebElement loginTopButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getLoginTopButton());
                loginTopButton.click();
                Thread.sleep((long) (Math.random() * 3000));

                while (getDriver().getCurrentUrl().equals(urls.homePage)) {// 不断的获取地址判断一下，地址有没有变
                    Thread.sleep(1000);
                    // 页面没有跳转就让他等待，等待自己重定向到登录后的页面，然后再获取cookie时就是正确的cookie
                }

            }
            final String loginOrVerifyUrl = getDriver().getCurrentUrl();// 获取跳转后的url地址

//            new WebDriverWait(getDriver(), Duration.ofSeconds(6)).until(ExpectedConditions.urlContains("member.lazada.sg/user/verification-pc"));
            if (loginOrVerifyUrl.contains("member.lazada.sg/user/verification-pc")) {
                verifyLoginByEmail();
            } else {
                inputAccountAndPassword();
                while (!getDriver().getCurrentUrl().equals(urls.homePage)) {// 不断的获取地址判断一下，地址有没有变
                    // todo
                    Thread.sleep(1000);
                    // 页面没有跳转就让他等待，等待自己重定向到登录后的页面，然后再获取cookie时就是正确的cookie
                }
            }
        } catch (Exception e) {
            this.logined = false;
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
        handleSuccessLogin();
        return true;
    }

    private void handleSuccessLogin() throws InterruptedException, JsonProcessingException {
        this.logined = true;
        changeRegion();
        updateCookiesInHeaders(getDriverCookies());
        saveCookiesToBuyerAccount(getDriverCookies());
        updateLoginTime();
    }

    private void updateLoginTime() {
        buyerAccount.setLastLoginTime(DateTime.now());
        buyerAccountService.save(buyerAccount);
    }

    private void verifyLoginByEmail() throws InterruptedException, MessagingException, IOException {
        inputAccountAndPassword();

        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(6)).until(ExpectedConditions.urlContains("member.lazada.sg/user/verification-pc"));
        } catch (Exception ex) {

        }

        List<WebElement> verify_buttons = SeleniumHelper.listByClassName(getDriver(), "verify-item");
        WebElement email_verify_button = verify_buttons.stream().filter(b -> b.getText().equals("Email Verification")).findFirst().orElse(null);
//             SeleniumHelper.getByXpath(getDriver(), xpaths.getAccountVerifyButton());
        email_verify_button.click();

        WebElement email_send_button = SeleniumHelper.getByXpath(getDriver(), xpaths.getVerifyEmailSendButton());
        email_send_button.click();

        Thread.sleep(18000);

        String accountVerifyEmailAddress = getBuyerAccount().getVerifyEmail();
        String accountVerifyEmailPassword = getBuyerAccount().getVerifyEmailPassword();
        String code = emailService.getEmailVerifyCode(accountVerifyEmailAddress, accountVerifyEmailPassword);

        if (code != "") {
            WebElement email_code_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getVerifyEmailCodeInput());
            SeleniumHelper.clearAndType(email_code_input, code);

            Thread.sleep((long) (Math.random() * 3000));

            WebElement verifySubmitButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getVerifySubmitButton());
            verifySubmitButton.click();
        }
    }

    private void inputAccountAndPassword() throws InterruptedException {
        WebElement email_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getAccountInput());
        SeleniumHelper.clearAndType(email_input, buyerAccount.getEmail());
        Thread.sleep((long) (Math.random() * 3000));

        WebElement password_input = SeleniumHelper.getByXpath(getDriver(), xpaths.getPasswordInput());
        SeleniumHelper.clearAndType(password_input, buyerAccount.getPassword());
        Thread.sleep((long) (Math.random() * 3000));

        WebElement login_button = SeleniumHelper.getByXpath(getDriver(), xpaths.getLoginButton());
        login_button.click();
        Thread.sleep(8888);
    }

    public boolean loginWithCookie() {
        getDriver().get(urls.homePage);
        try {
            var mycookies = getBuyerAccountCookie();
            for (var myCookie : mycookies) {
                var cookie = new Cookie(myCookie.name, myCookie.value);
                getDriver().manage().addCookie(cookie);
            }
            getDriver().get(urls.homePage);
            String currentUrl = getDriver().getCurrentUrl();// 获取跳转后的url地址
            if (currentUrl.startsWith(urls.loginPage)) {
                var html = getDriver().getPageSource();
                if (html.contains("You are now signed in to your account")) {
                    var accessNowButton = SeleniumHelper.getByXpath(getDriver(), xpaths.loginAccessNowButton);
                    accessNowButton.click();
                    Thread.sleep(500);
                    html = getDriver().getPageSource();
                    if (html.contains("Your account name or password is incorrect.")) {
//                        var signInOtherAccountBtn = SeleniumHelper.getByXpath(getDriver(), xpaths.signInOtherAccountButton);
//                        signInOtherAccountBtn.click();
                        for (var myCookie : mycookies) {
                            var cookie = new Cookie(myCookie.name, myCookie.value);
                            getDriver().manage().deleteCookie(cookie);
                        }
                        return false;
                    }
                    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(8L));
                    wait.until(ExpectedConditions.urlContains(urls.homePage));
                    return true;
                } else {
                    return false;
                }
            }
            if (!currentUrl.startsWith(urls.homePage)) {
                return false;
            }

            WebElement accountTopButtonSpan = SeleniumHelper.getByXpath(getDriver(), xpaths.getAccountTopButtonSpan());
            String spanText = accountTopButtonSpan.getText();
            if (spanText.contains("ACCOUNT")) {
                return true;
            } else {
                return false;
            }
        } catch (JsonProcessingException e) {
            log.debug("存储的cookie string解析失败，请检查！" + buyerAccount.getEmail());
            return false;
        } catch (TimeoutException e) {
            log.info("selenium超时错误,将重试...", e);
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private MyCookie[] getBuyerAccountCookie() throws JsonProcessingException {
        String cookiesStr = buyerAccount.getLoginCookie();
//        cookiesStr = cookiesStr.replace("httpOnly", "isHttpOnly").replace("secure", "isSecure");
        if (cookiesStr != null && !cookiesStr.equals("")) {
            return new ObjectMapper().readValue(cookiesStr, MyCookie[].class);
        }
        return new MyCookie[]{};
    }

    @Override
    public String getBotName() {
        return "BOT" + "__" + request.getOrderInfo().getOrderid() + "__" + request.getOrderInfo().getOrderItemId() + "：";
    }

    /**
     * @param request
     * @return
     */
    @Override
    public boolean doStock(StockRequest request) throws InterruptedException, JsonProcessingException {
        this.request = request;
        log.info(getBotName() + "开始下单操作.");

        var supplier = request.getSupplier();
        if (!supplier.getAvailable()) {
            log.info(getBotName() + "供应商开关未打开.");
            throw new SupplierUnavailableException();
        }


        loginAndPlaceOrder();

        return false;
    }

    public String getProductHtmlSource(String url) throws IOException, InterruptedException {
        var cookieSet = getBuyerAccountCookie();
        updateCookiesInHeaders(cookieSet);
        var html = reqeustHtml(url);
        if (html.contains("Buy Now")) {
            updateLoginTime();
            return html;
        }

        login();

        return reqeustHtml(url);
    }

    public String getSkuProperties(String html) {
        var jsonStr = "";
        Document doc = Jsoup.parse(html);
        Elements tds = doc.getElementsByTag("script"); // 标识获取html中第一个<script>标签
        for (var element : tds) {
            var content = element.data();
            content = content.trim();
            if (content.startsWith("window.runParams")) {
                jsonStr = content.split("window.runParams = \\{")[1];
                jsonStr = jsonStr.trim();
                jsonStr = content.split("data: ")[1];
                jsonStr = jsonStr.split("csrfToken: ")[0];
                jsonStr = jsonStr.trim();
                jsonStr = StringUtils.removeEnd(jsonStr, ",");
                System.out.println("json:" + jsonStr);
            }
        }
        return jsonStr;
    }

    private String reqeustHtml(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(savedHeaders);
        HttpEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                byte[].class
        );
        byte[] data = RestTemplateHelper.unGZip(new ByteArrayInputStream(response.getBody()));

        var result = new String(data, "UTF-8");

        return result;
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

    public void cartAdd(List<AddCartRequestModel> model) throws InterruptedException {
//        String paramMap = "[{\"itemId\":\"2199811436\",\"skuId\":\"14121812860\",\"quantity\":8}]";

        while (cartCount() > 0) {
            cartClear();
            Thread.sleep(1000);
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

    public void clickBuyNow() throws InterruptedException {
        var productPage = request.getSupplier().getUrl();
        getDriver().get(productPage);
        var styleList = request.getSupplier().getStyleName().split(",");
        var skuPanels = SeleniumHelper.listByClassName(getDriver(), "product-sku");
        for (int i = 0; i < styleList.length; i++) {
            var panel = skuPanels.get(i);
            var skuImgList = SeleniumHelper.listByRelativeXpath(getDriver(), panel, ".//img");
            for (var img : skuImgList) {
                var styleTitle = img.getAttribute("title");
                if (styleTitle != null & styleTitle.equals(styleList[i])) {
                    img.click();
                    boolean isSelected = false;
                    while (!isSelected) {
                        var imgParentLi = SeleniumHelper.getByRelativeXpath(getDriver(), img, ".//../..");
                        var className = imgParentLi.getAttribute("class");
                        if (className == null) {
                            continue;
                        }
                        if (className.contains("selected")) {
                            isSelected = true;
                        } else {
                            img.click();
                        }
                    }
                }
            }
        }

        WebElement amountDiv = SeleniumHelper.getByClassName(getDriver(), "product-quantity");
        var amountInput = SeleniumHelper.getByRelativeXpath(getDriver(), amountDiv, ".//input");
        SeleniumHelper.clearAndType(amountInput, request.getOrderInfo().getQuantity().toString());
        Thread.sleep(2333);

        var buyNowButtonDiv = SeleniumHelper.getByClassName(getDriver(), "product-action");
        var buyNowButton = SeleniumHelper.getByRelativeXpath(getDriver(), buyNowButtonDiv, ".//button");
        buyNowButton.click();
        Thread.sleep(2333);
    }

    public void checkout() throws InterruptedException {

        while (!getDriver().getCurrentUrl().contains(urls.confirmPaymentPage)) {
            log.info("等待进入confirm页面...");
            Thread.sleep(1000);
        }

        var quantityToBuy = BigDecimal.valueOf(request.getOrderInfo().getQuantity());

        WebElement orderSummaryPriceDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getSubtotalDiv());
        var text = orderSummaryPriceDiv.getText();
        var subTotalPrice = parseUSDMoney(text);

        var supplierPrice = new BigDecimal(request.getSupplier().getPrice()).multiply(quantityToBuy);
        log.info("供应商价格:$" + supplierPrice);
        log.info("本次购买数量:" + quantityToBuy);
        log.info("页面显示的价格:" + subTotalPrice);
        if (subTotalPrice.compareTo(supplierPrice) > 0) {
            throw new WrongStockPriceException("we got sub total price without ship fee:" + subTotalPrice + ", but the supplier price*quantity is:" + supplierPrice);
        }

        WebElement shippingFeeDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getShippingFeeDiv());
        text = shippingFeeDiv.getText();
        text = text.equals("Free") ? "US $0" : text;
        var shippingFee = parseUSDMoney(text);
        var minSupplierShippingFee = new BigDecimal(request.getSupplier().getMinShipFee());
        if (shippingFee.compareTo(minSupplierShippingFee) > 0) {
            throw new OverTopShipFeeException("we got a ship fee:" + shippingFee);
        }


    }

    public void payNow() throws InterruptedException {
        if (paySwitch) {
            WebElement totalPriceDiv = SeleniumHelper.getByXpath(getDriver(), xpaths.getTotalPriceDiv());
            var totalPrice = parseUSDMoney(totalPriceDiv.getText());

            WebElement payNowButton = SeleniumHelper.getByXpath(getDriver(), xpaths.payNowButton);
            payNowButton.click();
            var succTips = "Payment Successful";
            int waitTick = 0;
            while (!getDriver().getPageSource().contains(succTips)) {
                if (waitTick > 66) {
                    throw new PayFailedException("付款失败！请检查详情！");
                }
                log.info("等待付款成功的提示...");
                waitTick++;
                Thread.sleep(1000);
            }
            request.getStockStatus().setStatus(StatusEnum.stockedUnshipped.name());
            request.getStockStatus().setStockTime(DateTime.now());
            request.getStockStatus().setQuantity(request.getOrderInfo().getQuantity());
            request.getStockStatus().setTotalPrice(totalPrice);
            stockStatusService.save(request.getStockStatus());
            buyerAccount.setOrderCount(buyerAccount.getOrderCount() + 1);
            buyerAccountService.save(buyerAccount);
        } else {
            throw new PayFailedException("付款开关未打开，实际没有付款！");
        }
    }

    private void initHeaders() {
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
        this.savedHeaders = headers;
    }

    private HttpHeaders getCainiaoGlobalHeaders() {
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
        return headers;
    }

    protected void finalize() {
        getDriver().quit();
    }
}
