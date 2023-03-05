/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.aliexpressbot.model.logistic.aliexpress;

import java.util.List;

/**
 * Auto-generated: 2023-03-05 12:19:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Data {

    private boolean grayFlag;
    private String userType;
    private List<Packages> packages;

    public boolean getGrayFlag() {
        return grayFlag;
    }

    public void setGrayFlag(boolean grayFlag) {
        this.grayFlag = grayFlag;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<Packages> getPackages() {
        return packages;
    }

    public void setPackages(List<Packages> packages) {
        this.packages = packages;
    }

}