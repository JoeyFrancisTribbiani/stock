/**
  * Copyright 2023 ab173.com 
  */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyComponent {

    private String baseCurrencyCode;
    private boolean enableTransaction;
    private String currencySymbol;
    private boolean symbolFront;
    private double currencyRate;
    private boolean baseSymbolFront;
    private String currencyCode;
    private String baseCurrencySymbol;
    private boolean multiCurrency;
    public void setBaseCurrencyCode(String baseCurrencyCode) {
         this.baseCurrencyCode = baseCurrencyCode;
     }
     public String getBaseCurrencyCode() {
         return baseCurrencyCode;
     }

    public void setEnableTransaction(boolean enableTransaction) {
         this.enableTransaction = enableTransaction;
     }
     public boolean getEnableTransaction() {
         return enableTransaction;
     }

    public void setCurrencySymbol(String currencySymbol) {
         this.currencySymbol = currencySymbol;
     }
     public String getCurrencySymbol() {
         return currencySymbol;
     }

    public void setSymbolFront(boolean symbolFront) {
         this.symbolFront = symbolFront;
     }
     public boolean getSymbolFront() {
         return symbolFront;
     }

    public void setCurrencyRate(double currencyRate) {
         this.currencyRate = currencyRate;
     }
     public double getCurrencyRate() {
         return currencyRate;
     }

    public void setBaseSymbolFront(boolean baseSymbolFront) {
         this.baseSymbolFront = baseSymbolFront;
     }
     public boolean getBaseSymbolFront() {
         return baseSymbolFront;
     }

    public void setCurrencyCode(String currencyCode) {
         this.currencyCode = currencyCode;
     }
     public String getCurrencyCode() {
         return currencyCode;
     }

    public void setBaseCurrencySymbol(String baseCurrencySymbol) {
         this.baseCurrencySymbol = baseCurrencySymbol;
     }
     public String getBaseCurrencySymbol() {
         return baseCurrencySymbol;
     }

    public void setMultiCurrency(boolean multiCurrency) {
         this.multiCurrency = multiCurrency;
     }
     public boolean getMultiCurrency() {
         return multiCurrency;
     }

}