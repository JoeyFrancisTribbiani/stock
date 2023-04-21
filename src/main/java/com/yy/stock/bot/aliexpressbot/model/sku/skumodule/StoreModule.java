/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

/**
 * Auto-generated: 2023-04-21 12:4:52
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StoreModule {

    private long buyerAdminSeq;
    private long companyId;
    private String countryCompleteName;
    private String detailPageUrl;
    private boolean esRetailOrConsignment;
    private String feedbackMessageServer;
    private String feedbackServer;
    private boolean followed;
    private int followingNumber;
    private boolean hasStore;
    private boolean hasStoreHeader;
    private boolean hideCustomerService;
    private I18nMap i18nMap;
    private int id;
    private String name;
    private String openTime;
    private int openedYear;
    private int positiveNum;
    private String positiveRate;
    private long productId;
    private long sellerAdminSeq;
    private String sessionId;
    private String siteType;
    private String storeName;
    private long storeNum;
    private String storeURL;
    private String topBrandDescURL;
    private boolean topRatedSeller;


}