package com.yy.stock.dto;

import com.yy.stock.entity.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor

@Getter
@Setter
public class TrackRequest {
    private StockStatus stockStatus;
}