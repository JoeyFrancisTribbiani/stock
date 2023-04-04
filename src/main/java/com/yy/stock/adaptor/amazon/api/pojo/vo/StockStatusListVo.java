package com.yy.stock.adaptor.amazon.api.pojo.vo;

import com.yy.stock.entity.Platform;
import com.yy.stock.entity.Supplier;
import lombok.Data;

import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
public class StockStatusListVo extends AmzProductListVo {
    Supplier supplier;
    Platform platform;
}
