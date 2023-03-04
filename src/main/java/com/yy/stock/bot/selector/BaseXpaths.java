package com.yy.stock.bot.selector;

import lombok.Data;

@Data
public class BaseXpaths {
    // 登录相关
    public String loginTopButton;
    public String accountInput;
    public String accountTopButtonSpan;
    public String passwordInput;
    public String loginButton;
    public String accountVerifyButton;
    public String verifyEmailSendButton;
    public String verifyEmailCodeInput;
    public String verifySubmitButton;

    // 购物车相关
    public String selectAllCartItemsDiv;
    public String selectAllCartItemsButton;
    public String deleteAllCartItemsButton;
    public String deleteCartItemsConfirmButton;
    public String cartEmptyTipsDiv;
    public String checkoutButton;

    // 地址相关
    public String deleteAddressButton;
    public String confirmDeleteAddressButton;
    public String addAddressButton;
    public String addressNameInput;
    public String addressPhoneInput;
    public String addressCepInput;
    public String addressCepSelectButton;
    public String addressBairrorInput;
    public String addressRuaInput;
    public String addressRuaNumInput;
    public String addressNoteInput;
    public String addressCpfInput;
    public String addressSetDefaultButton;
    public String addressSaveButton;
    public String addressFirstAddressNameDiv;

    // 购买结算页面
    public String buyNowButton;
    public String confirmAddressNameDiv;
    public String subtotalDiv;
    public String shippingFeeDiv;
    public String totalPriceDiv;
    public String payNowButton;
}
