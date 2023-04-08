package com.yy.stock.dto;

public enum StockStatusEnum {
    unstocked,
    stocking,
    stockFailed,
    payedButInfoSaveError,
    stockedUnshipped,
    shipped,
    delivered,
    unstockedAskReturn,
    stockedUnshippedAskReturn,
    shippedAskReturn,
    shippedLosed,
    deliveredAskReturn

}
