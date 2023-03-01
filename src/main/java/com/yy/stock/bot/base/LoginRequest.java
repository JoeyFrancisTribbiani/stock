package com.yy.stock.bot.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    public String account;
    public String password;
}
