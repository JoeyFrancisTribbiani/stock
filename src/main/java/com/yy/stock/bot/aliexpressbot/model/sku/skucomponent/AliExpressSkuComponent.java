/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliExpressSkuComponent {

    private TradeComponent tradeComponent;
    private PageSizeComponent pageSizeComponent;
    private RedirectComponent redirectComponent;
    private MetaDataComponent metaDataComponent;
    private PlazaSellerServiceComponent plazaSellerServiceComponent;
    private ProductPropComponent productPropComponent;
    private SkuComponent skuComponent;
    private WebActionConfComponent webActionConfComponent;
    private PackageComponent packageComponent;
    //    private ProductTagComponent productTagComponent;
    private BlacklistComponent blacklistComponent;
    private PriceComponent priceComponent;
    private WebLongImageComponent webLongImageComponent;
    private WishListComponent wishListComponent;
    //    private MultiLanguageUrlComponent multiLanguageUrlComponent;
//    private I18nComponent i18nComponent;
    private CategoryComponent categoryComponent;
    private ProductInfoComponent productInfoComponent;
    private SellerGuaranteeComponent sellerGuaranteeComponent;
    private BuyerComponent buyerComponent;
    private StoreHeaderComponent storeHeaderComponent;
    private BreadcrumbComponent breadcrumbComponent;
    private SimpleBannerComponent simpleBannerComponent;
    private AbTestComponent abTestComponent;
    private GagaComponent gagaComponent;
    private SiteInfoComponent siteInfoComponent;
    private RemindsComponent remindsComponent;
    private ShopCategoryComponent shopCategoryComponent;
    private PromotionComponent promotionComponent;
    private SellerPromiseComponent sellerPromiseComponent;
    //    private ExtraComponent extraComponent;
    private StoreFeedbackComponent storeFeedbackComponent;
    private WebGeneralFreightCalculateComponent webGeneralFreightCalculateComponent;
    private AssuranceComponent assuranceComponent;
    private PriceRuleComponent priceRuleComponent;
    private InventoryComponent inventoryComponent;
    private WebCouponPriceComponent webCouponPriceComponent;
    private InstallmentComponent installmentComponent;
    private ProductDescComponent productDescComponent;
    private CategoryTagComponent categoryTagComponent;
    private SupplementInfoLayoutComponent supplementInfoLayoutComponent;
    private ImageComponent imageComponent;
    private RecommendComponent recommendComponent;
    private UserComponent userComponent;
    private CurrencyComponent currencyComponent;
    private ItemStatusComponent itemStatusComponent;
    private ReferComponent referComponent;
    private FeedbackComponent feedbackComponent;
    private VehicleComponent vehicleComponent;
    private DisplayTitleComponent displayTitleComponent;
}