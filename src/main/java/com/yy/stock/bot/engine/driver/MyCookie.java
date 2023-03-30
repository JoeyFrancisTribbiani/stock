package com.yy.stock.bot.engine.driver;

import lombok.Data;

import java.util.Date;

@Data
public class MyCookie {
    public String name;
    public String value;
    public String path;
    public String domain;
    public Date expiry;
    public boolean secure;
    public boolean httpOnly;
    public String sameSite;
}
