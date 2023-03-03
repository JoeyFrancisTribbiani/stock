package com.yy.stock.common.exception;

public class WrongCartItemsCountAddedException extends InterruptedException {
    public WrongCartItemsCountAddedException(String s) {
        super(s);
    }
}
