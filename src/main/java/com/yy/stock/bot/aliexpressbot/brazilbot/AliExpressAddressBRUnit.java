package com.yy.stock.bot.aliexpressbot.brazilbot;

import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.stocker.AddressUnit;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AliExpressAddressBRUnit extends AddressUnit {

    public AliExpressAddressBRUnit(CoreEngine coreEngine) {
        super(coreEngine);
    }

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
        var addAddressButton = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addAddressButton);
        addAddressButton.click();
        Thread.sleep(6666);


        var nameInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressNameInput);
        var name = cleanAddressName(stockRequest.getAddress().getName());
        if (name.length() > 49) {
            name = name.substring(0, 49);
        }
        nameInput.sendKeys(name);
        Thread.sleep(1000);

        var phoneInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressPhoneInput);
        var phone = cleanAddressPhone(stockRequest.getAddress().getPhone());
        phoneInput.sendKeys(phone);
        Thread.sleep(1000);

        var cepInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressCepInput);
        cepInput.sendKeys(stockRequest.getAddress().getPostalCode());
        Thread.sleep(1000);
        var cepSelectButton = driverEngine.getExecutor().getByClassName("postcode-option-item-content");
        cepSelectButton.click();
        Thread.sleep(3000);

        var bairrorInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressBairrorInput);
        var bairror = stockRequest.getAddress().getAddressLine2();
        if (bairror != null && !bairror.equals("")) {
            if (bairror.length() > 49) {
                bairror = bairror.substring(bairror.length() - 30);
            }
            bairror = bairror.replace(',', ' ');
            driverEngine.getExecutor().clearAndType(bairrorInput, bairror);
        } else {
            var autoText = bairrorInput.getAttribute("value");
            if (autoText.equals("")) {
                driverEngine.getExecutor().clearAndType(bairrorInput, "Casa");
            }
        }
        Thread.sleep(1000);

        var ruaInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressRuaInput);
        String rua = cleanAddressRua(stockRequest.getAddress().getAddressLine1());
        var ruaWords = rua.split(" ");
        var ruaNum = ruaWords[ruaWords.length - 1];
        if (rua.length() > 1) {
            if (driverEngine.getExecutor().isNumberString(ruaNum)) {
                ruaWords = Arrays.copyOf(ruaWords, ruaWords.length - 1);
            } else {
                ruaNum = "0";
            }
            rua = String.join(" ", ruaWords);
        } else {
            ruaNum = "0";
        }
        if (!rua.equals("")) {
            driverEngine.getExecutor().clearAndType(ruaInput, rua);
            Thread.sleep(1000);
        }
        var ruaNumInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressRuaNumInput);
        driverEngine.getExecutor().clearAndType(ruaNumInput, ruaNum);
        Thread.sleep(1000);

        var noteInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressNoteInput);
        var note = stockRequest.getAddress().getAddressType();
        if (note != null && !note.equals("")) {
            note = note.replace(',', ' ');
        }
        driverEngine.getExecutor().clearAndType(noteInput, note);
        Thread.sleep(1000);

        var cpfInput = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressCpfInput);
        // 使用此字段存储巴西cpf
        var cpf = stockRequest.getAddress().getDistrict();
        cpf = cleanAddressCpf(cpf);
        driverEngine.getExecutor().clearAndType(cpfInput, cpf);
        Thread.sleep(1000);

        var setDefaultButton = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressSetDefaultButton);
        setDefaultButton.sendKeys(Keys.SPACE);
        Thread.sleep(1000);

        var saveButton = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.addressSaveButton);
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

    /**
     *
     */

}
