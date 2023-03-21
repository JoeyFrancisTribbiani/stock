package com.yy.stock.common.exception;

public class PayFailedException extends InterruptedException {
    public PayFailedException(String s) {
        super(s);
    }
}
