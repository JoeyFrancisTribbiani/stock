package com.yy.stock.bot.engine.driver;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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
