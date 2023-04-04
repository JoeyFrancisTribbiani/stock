package com.yy.stock.bot.selector;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseXpaths {
    // 登录相关
    public String loginTopButton;
    public String accountInput;
    public String accountTopButtonSpan;
    public String passwordInput;
    public String loginButton;
    public String loginAccessNowButton;
    public String signInOtherAccountButton;
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
    public String addressRegionSelectDiv;
    public String regionCheckListButton;
    public String regionArrayLis;
    public String regionSaveButton;
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

    // 订单相关
    public String firstOrderIdLabel;
}
