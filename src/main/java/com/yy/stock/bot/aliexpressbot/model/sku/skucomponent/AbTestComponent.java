/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbTestComponent {

    private int detailNewVersion;
    private boolean openDescHover;
    private boolean hideCustomerService;
    private boolean rmStoreLevelAB;

    public int getDetailNewVersion() {
        return detailNewVersion;
    }

    public void setDetailNewVersion(int detailNewVersion) {
        this.detailNewVersion = detailNewVersion;
    }

    public boolean getOpenDescHover() {
        return openDescHover;
    }

    public void setOpenDescHover(boolean openDescHover) {
        this.openDescHover = openDescHover;
    }

    public boolean getHideCustomerService() {
        return hideCustomerService;
    }

    public void setHideCustomerService(boolean hideCustomerService) {
        this.hideCustomerService = hideCustomerService;
    }

    public boolean getRmStoreLevelAB() {
        return rmStoreLevelAB;
    }

    public void setRmStoreLevelAB(boolean rmStoreLevelAB) {
        this.rmStoreLevelAB = rmStoreLevelAB;
    }

}