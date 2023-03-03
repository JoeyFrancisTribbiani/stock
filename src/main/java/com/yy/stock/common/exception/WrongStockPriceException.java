package com.yy.stock.common.exception;

public class WrongStockPriceException extends InterruptedException {
    public WrongStockPriceException(String s) {
        super(s);
    }
}
