/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.adaptor.seleniumgrid.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Capabilities {

    private boolean acceptInsecureCerts;
    private String browserName;
    private String browserVersion;
    private Chrome chrome;
    private GoogchromeOptions googchromeOptions;
    private boolean networkConnectionEnabled;
    private String pageLoadStrategy;
    private String platformName;
    private Proxy proxy;
    private boolean sebidiEnabled;
    private String secdp;
    private String secdpVersion;
    private boolean setWindowRect;
    private boolean strictFileInteractability;
    private Timeouts timeouts;
    private String unhandledPromptBehavior;
    private boolean webauthnextensioncredBlob;
    private boolean webauthnextensionlargeBlob;
    private boolean webauthnextensionminPinLength;
    private boolean webauthnextensionprf;
    private boolean webauthnvirtualAuthenticators;

}