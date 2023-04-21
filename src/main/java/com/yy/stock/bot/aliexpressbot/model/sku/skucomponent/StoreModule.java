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
public class StoreModule {
    private String COUSTOMER_SERVICE;
    private String VISIT_STORE;
    private String CONTACT_SELLER;
    private String FOLLOWING_STATE;
    private String UNFOLLOWING_STATE;
    private String POSITIVE_FEEDBACK;
    private String FOLLOWERS;
    private String FOLLOWER;
    private String TOP_SELLER;
    private String STORE_CATEGORIES;

    public String getCOUSTOMER_SERVICE() {
        return COUSTOMER_SERVICE;
    }

    public void setCOUSTOMER_SERVICE(String COUSTOMER_SERVICE) {
        this.COUSTOMER_SERVICE = COUSTOMER_SERVICE;
    }

    public String getVISIT_STORE() {
        return VISIT_STORE;
    }

    public void setVISIT_STORE(String VISIT_STORE) {
        this.VISIT_STORE = VISIT_STORE;
    }

    public String getCONTACT_SELLER() {
        return CONTACT_SELLER;
    }

    public void setCONTACT_SELLER(String CONTACT_SELLER) {
        this.CONTACT_SELLER = CONTACT_SELLER;
    }

    public String getFOLLOWING_STATE() {
        return FOLLOWING_STATE;
    }

    public void setFOLLOWING_STATE(String FOLLOWING_STATE) {
        this.FOLLOWING_STATE = FOLLOWING_STATE;
    }

    public String getUNFOLLOWING_STATE() {
        return UNFOLLOWING_STATE;
    }

    public void setUNFOLLOWING_STATE(String UNFOLLOWING_STATE) {
        this.UNFOLLOWING_STATE = UNFOLLOWING_STATE;
    }

    public String getPOSITIVE_FEEDBACK() {
        return POSITIVE_FEEDBACK;
    }

    public void setPOSITIVE_FEEDBACK(String POSITIVE_FEEDBACK) {
        this.POSITIVE_FEEDBACK = POSITIVE_FEEDBACK;
    }

    public String getFOLLOWERS() {
        return FOLLOWERS;
    }

    public void setFOLLOWERS(String FOLLOWERS) {
        this.FOLLOWERS = FOLLOWERS;
    }

    public String getFOLLOWER() {
        return FOLLOWER;
    }

    public void setFOLLOWER(String FOLLOWER) {
        this.FOLLOWER = FOLLOWER;
    }

    public String getTOP_SELLER() {
        return TOP_SELLER;
    }

    public void setTOP_SELLER(String TOP_SELLER) {
        this.TOP_SELLER = TOP_SELLER;
    }

    public String getSTORE_CATEGORIES() {
        return STORE_CATEGORIES;
    }

    public void setSTORE_CATEGORIES(String STORE_CATEGORIES) {
        this.STORE_CATEGORIES = STORE_CATEGORIES;
    }

}