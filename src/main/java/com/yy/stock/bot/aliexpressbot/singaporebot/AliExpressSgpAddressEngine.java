package com.yy.stock.bot.aliexpressbot.singaporebot;

import com.yy.stock.bot.engine.stocker.AddressEngine;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AliExpressSgpAddressEngine extends AddressEngine {
    @Override
    protected void deleteOldAddress() {
        driverEngine.getDriver().get(coreEngine.urls.addressListPage);
        try {
            var deleteAddressButton = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.deleteAddressButton);
            deleteAddressButton.click();
            Thread.sleep(2333);
            var confirmDeleteAddressButton = driverEngine.getExecutor().getByClassName("next-dialog-btn");
            confirmDeleteAddressButton.click();
            Thread.sleep(5000);
        } catch (Exception ex) {
            log.info(coreEngine.getBotName() + "只有一个地址，无需删除.");
        }
    }

    @Override
    protected void inputAddressInfo() throws InterruptedException {
//        var confirmDeleteAddressButton = driverEngine.getExecutor().getByXpath( coreEngine.xpaths.confirmDeleteAddressButton);
        try {
            var addAddressButton = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addAddressButton);
            addAddressButton.click();
            Thread.sleep(6666);
        } catch (Exception ex) {
            log.info(coreEngine.getBotName() + "未找到添加地址按钮，默认为无任何地址，直接添加信息.");
        }


        var nameInput = driverEngine.getExecutor().getByXpath("//*[@id=\"halo-wrapper-root\"]/div/div/form/div[2]/div[2]/div/div[1]/div/div/span/input");
        var name = cleanAddressName(stockRequest.getAddress().getName());
        if (name.length() > 49) {
            name = name.substring(0, 49);
        }
        nameInput.sendKeys(name);
        Thread.sleep(1000);

        var phoneInput = driverEngine.getExecutor().getByXpath("//*[@id=\"halo-wrapper-root\"]/div/div/form/div[2]/div[2]/div/div[2]/div/div/span/span[2]/input");
        var phone = cleanAddressPhone(stockRequest.getAddress().getPhone());
        phoneInput.sendKeys(phone);
        Thread.sleep(1000);

        var cepInput = driverEngine.getExecutor().getByXpath("//*[@id=\"halo-wrapper-root\"]/div/div/form/div[3]/div[2]/div[1]/div/div/div/span/span[1]/input");
        cepInput.sendKeys(stockRequest.getAddress().getPostalCode());
        Thread.sleep(1000);
        var cepSelectButton = driverEngine.getExecutor().getByClassName("postcode-option-item-content");
//        var html = driverEngine.getDriver().getPageSource();
//        var doc = Jsoup.parse(html);
        var listBoxUl = driverEngine.getExecutor().getByRelativeXpath(cepSelectButton, "./../../../..");
        var listBoxLis = listBoxUl.findElements(By.tagName("li"));
        var longestTextLi = listBoxLis.get(0);
        var longestText = longestTextLi.getText();
        for(var listBoxLi : listBoxLis){
            var listBoxLiText = listBoxLi.getText();
            var textLength = listBoxLiText.length();
            if(textLength > longestText.length()){
                longestText = listBoxLiText;
                longestTextLi = listBoxLi;
            }
        }
        longestTextLi.click();
//        cepSelectButton.click();
        Thread.sleep(3000);

        var bairrorInput = driverEngine.getExecutor().getByXpath("//*[@id=\"halo-wrapper-root\"]/div/div/form/div[3]/div[2]/div[2]/div[2]/div/div/span/input");
        var bairror = stockRequest.getAddress().getAddressLine1() + " " + stockRequest.getAddress().getAddressLine2();
        if (bairror != null && !bairror.equals("")) {
            if (bairror.length() > 49) {
                bairror = bairror.substring(bairror.length() - 30);
            }
            bairror = bairror.replace(',', ' ');
            driverEngine.getExecutor().clearAndType(bairrorInput, bairror);
        } else {
            var autoText = bairrorInput.getAttribute("value");
            if (autoText.equals("")) {
                driverEngine.getExecutor().clearAndType(bairrorInput, "");
            }
        }
        Thread.sleep(1000);

        var cityInput = driverEngine.getExecutor().getByXpath("//*[@id=\"halo-wrapper-root\"]/div/div/form/div[3]/div[2]/div[3]/div[2]/div/div/span/input");
        driverEngine.getExecutor().clearAndType(cityInput, "Singapore");
//        String rua = cleanAddressRua(stockRequest.getAddress().getAddressLine1());
//        var ruaWords = rua.split(" ");
//        var ruaNum = ruaWords[ruaWords.length - 1];
//        if (rua.length() > 1) {
//            if (driverEngine.getExecutor().isNumberString(ruaNum)) {
//                ruaWords = Arrays.copyOf(ruaWords, ruaWords.length - 1);
//            } else {
//                ruaNum = "0";
//            }
//            rua = String.join(" ", ruaWords);
//        } else {
//            ruaNum = "0";
//        }
//        if (!rua.equals("")) {
//            driverEngine.getExecutor().clearAndType(cityInput, rua);
//            Thread.sleep(1000);
//        }
//        var ruaNumInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressRuaNumInput);
//        driverEngine.getExecutor().clearAndType(ruaNumInput, ruaNum);
//        Thread.sleep(1000);

        var setDefaultButton = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressSetDefaultButton);
        setDefaultButton.sendKeys(Keys.SPACE);
        Thread.sleep(1000);

        var saveButton = driverEngine.getExecutor().getByXpath("//*[@id=\"halo-wrapper-root\"]/div/div/form/div[5]/div/div/button[1]");
        saveButton.click();
        Thread.sleep(2000);

        var firstAddressNameDiv = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressFirstAddressNameDiv);
        var peopleFirstName = name.split(" ")[0];
        while (!firstAddressNameDiv.getText().contains(peopleFirstName)) {
            firstAddressNameDiv = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressFirstAddressNameDiv);
            Thread.sleep(1000);
        }
        Thread.sleep(1000);
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

//        if (phone.charAt(phone.length() - 9) != '9' && (phone.length() == 10 || phone.length() == 12)) {
//            var sb = new StringBuilder(phone);
//            phone = sb.insert(phone.length() - 8, "9").toString();
//        }
//        if (phone.startsWith("55") && phone.length() > 11) {
//            phone = phone.substring(2);
//        }
//        if (phone.startsWith("0")) {
//            phone = phone.substring(1);
//        }
        return phone;
    }
}
