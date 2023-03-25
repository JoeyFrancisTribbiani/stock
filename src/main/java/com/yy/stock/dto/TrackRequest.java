package com.yy.stock.dto;

import com.yy.stock.entity.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TrackRequest {
    private StockStatus stockStatus;
}