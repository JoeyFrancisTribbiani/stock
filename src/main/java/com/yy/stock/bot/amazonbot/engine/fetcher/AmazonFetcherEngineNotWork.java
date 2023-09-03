package com.yy.stock.bot.amazonbot.engine.fetcher;

import com.alibaba.fastjson2.JSONArray;
import com.xxl.job.core.context.XxlJobHelper;
import com.yy.stock.adaptor.amazon.api.InventorySubmitFeedService;
import com.yy.stock.bot.amazonbot.engine.rester.AmazonResterEngine;
import com.yy.stock.bot.amazonbot.model.GatherEntrance;
import com.yy.stock.bot.amazonbot.model.TraversalStatus;
import com.yy.stock.common.util.MySpringUtil;
import com.yy.stock.dto.SkuModuleBase;
import com.yy.stock.entity.AmazonCategory;
import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.entity.AmazonSeller;
import com.yy.stock.service.AmazonCategoryService;
import com.yy.stock.service.AmazonSelectionHasFollowService;
import com.yy.stock.service.AmazonSelectionService;
import com.yy.stock.service.AmazonSellerService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class AmazonFetcherEngineNotWork {
    private AmazonSelectionService amazonSelectionService;
    private final AmazonSelectionHasFollowService amazonSelectionHasFollowService;
    private final AmazonCategoryService amazonCategoryService;
    private final InventorySubmitFeedService inventorySubmitFeedService;
    protected AmazonResterEngine resterEngine;
    private AmazonSellerService amazonSellerService;

    public AmazonFetcherEngineNotWork(){
        this.amazonSelectionService= MySpringUtil.getBean(AmazonSelectionService.class);
        this.amazonSellerService = MySpringUtil.getBean(AmazonSellerService.class);
        this.amazonSelectionHasFollowService=MySpringUtil.getBean(AmazonSelectionHasFollowService.class);
        this.amazonCategoryService=MySpringUtil.getBean(AmazonCategoryService.class);
        this.inventorySubmitFeedService=MySpringUtil.getBean(InventorySubmitFeedService.class);
        this.resterEngine=new AmazonResterEngine();
    }
    public SkuModuleBase fetch(String url) throws InterruptedException {
        String searchKey;
        if (url == null || url.equals("")) {
            searchKey = XxlJobHelper.getJobParam();
        } else {
            searchKey = url;
        }
        try {
            schedule(searchKey);

        } catch (Exception ex) {
            log.info("选品任务过程中报错,ex:" + ex.getMessage());
        }
        return null;
    }

    public void schedule(String searchUrl) throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        // searchUrl示例1：https://www.amazon.sg/gp/bestsellers/pet-supplies/14589236051/ref=zg_bs_nav_pet-supplies_3_14590078051
        // searchUrl示例2：https://www.amazon.sg/s?k=dd&ref=nb_sb_noss
        // searchUrl示例3：""


        // 如果为空则不是采集任务，而是取消无货源的跟卖ASIN
        if (searchUrl == null || searchUrl.equals("")) {
            log.info("开始取消无货源的跟卖ASIN");
            cancelUnavailableFollowedAsin();
            checkWinPurchaseButton();
            return;
        }

        log.info("开始搜索关键词：" + searchUrl);
        if (searchUrl.startsWith("FetchByCategory")) {
            String[] split = searchUrl.split(",");
            var marketplaceId = split[1];
            String categoryId = split[2];
            var amazonCategory = amazonCategoryService.getListByMarketplaceIdAndCategoryId(marketplaceId, categoryId).get(0);
            var categoryUrl = amazonCategory.getUrl();
            log.info("开始搜索类目：" + amazonCategory.getName());

            amazonCategory.setTraversalStatus(TraversalStatus.Progressing);
            amazonCategoryService.save(amazonCategory);

            fetchBestSellerCategoryAsins(categoryUrl);

            amazonCategory.setTraversalStatus(TraversalStatus.Done);
            amazonCategory.setLastTraversalTime(new Date());
            amazonCategoryService.save(amazonCategory);
            return;
        }

        if (searchUrl.contains("gp/bestsellers")) {
            fetchBestSeller(searchUrl);
            return;
        }

        // seller home page url like:https://www.amazon.sg/sp?marketplaceID=A19VAU5U5O7RUS&seller=A2JCL5BQO3UZK7&isAmazonFulfilled=1&ref_=dp_merchant_link&asin=B07YSVJ16T
        if (searchUrl.contains("seller=")) {
            fetchFollowBuddyAsins(searchUrl);
            return;
        }


        // 否则获取关键词搜索展示页的商品
        int maxPage = 3;
        for (int i = 1; i <= maxPage; i++) {
            searchUrl = searchUrl + "&page=" + i;
            fetchSearchAsins(searchUrl, searchUrl);
        }
    }

    public void cancelUnavailableFollowedAsin() throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        var toBeCanceledAsins = amazonSelectionHasFollowService.getToBeCancelled();
        log.info("待取消的跟卖ASIN数量：" + toBeCanceledAsins.size());
        for (var amazonSelectionHasFollow : toBeCanceledAsins) {
            inventorySubmitFeedService.submit(amazonSelectionHasFollow);
            amazonSelectionHasFollow.setHasFollowSell(false);
            amazonSelectionHasFollowService.save(amazonSelectionHasFollow);
            Thread.sleep(2000);
        }
    }

    public void checkWinPurchaseButton() throws InterruptedException, IOException {
        // 检查已跟卖的产品是否赢得购买按钮
        var authId = new BigInteger("1653288093023318018");
        var hasFollowed = amazonSelectionHasFollowService.getAllHasFollowed(authId);
        log.info("已跟卖的ASIN数量：" + hasFollowed.size());
        for (var amazonSelectionHasFollow : hasFollowed) {
            var selection = amazonSelectionService.getById(amazonSelectionHasFollow.getAmazonSelectionId());
            var url = selection.getUrl();
            var html= resterEngine.getStringResponse(url);
            Document doc = Jsoup.parse(html);
            var merchantInfoDiv = doc.getElementById("merchant-info");
            var merchantInfo = merchantInfoDiv.text();
            if (merchantInfo.contains("Heng Official")) {
                log.info("ASIN：" + selection.getAsin() + "已赢得购买按钮");
                amazonSelectionHasFollow.setWinPurchaseButton(true);
            } else {
                log.info("ASIN：" + selection.getAsin() + "未赢得购买按钮");
                amazonSelectionHasFollow.setWinPurchaseButton(false);
            }
            amazonSelectionHasFollowService.save(amazonSelectionHasFollow);
        }
    }

    private void fetchBestSeller(String url) throws InterruptedException, MalformedURLException {
        saveSubCategories(url);
    }

    public void saveSubCategories(String parentCategoryUrl) throws InterruptedException, MalformedURLException {
//        var searchUrl = "https://www.amazon.sg/gp/bestsellers/automotive/6394508051/ref=zg_bs_nav_automotive_1";
        if (amazonCategoryService == null) {
            amazonSelectionService = new AmazonSelectionService();
        }

        var url = new URL(parentCategoryUrl);
        var host = url.getHost();


        var html= resterEngine.getStringResponse(parentCategoryUrl);
        Document doc = Jsoup.parse(html);

        String marketplaceId = getMarketplaceIdFromUrl(parentCategoryUrl);

        var categoryItems = doc.getElementsByAttributeValue("role", "treeitem");
        var subCategorieItems = new Elements();
        for (int i = 0; i < categoryItems.size(); i++) {
            var item = categoryItems.get(i);
            var children = item.children();
            var child = children.get(0);
            var tagName = child.tagName();
            // span是当前分类，a是父子分类
            if (tagName.equals("span")) {
                var parentDiv = item.parent();
                var parentRole = parentDiv.attr("role");

                // 如果当前分类所在的item，父级div角色是group，说明当前分类已经没有子分类，而是和兄弟分类分在了同一个group，那么函数直接返回
                if (parentRole.equals("group")) {
                    return;
                } else {
                    // 如果当前分类所在的item，父级div没有role特性，说明当前分类还有子分类，继续递归
                    // 它下面的兄弟div是子分类集合且role为group
                    var nextDiv = item.nextElementSibling();
                    //q:sibling是兄弟节点，而children是子节点，为什么这里用nextElementSibling()而不用children()?
                    //a:因为children()只能获取直接子节点，而nextElementSibling()可以获取兄弟节点
                    var nextRole = nextDiv.attr("role");
                    if (nextRole.equals("group")) {
                        subCategorieItems = nextDiv.children();
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < subCategorieItems.size(); i++) {
            var item = subCategorieItems.get(i);
            var children = item.children();
            var child = children.get(0);
            var tagName = child.tagName();
            if (tagName.equals("a")) {
                var href = child.attr("href");
                var hrefArr = href.split("/");
                var link = "https://" + host + href;

                String categoryId;
                String idStr;
                String ancestorsCategoryId;
                String parentId = "";
                var categoryName = child.text();

                ancestorsCategoryId = hrefArr[3];

                idStr = hrefArr[4];

                // 说明是此子分类是祖先分类，存储ancestorCategoryId为id
                if (idStr.startsWith("ref=")) {
                    categoryId = ancestorsCategoryId;
                    parentId = "Root";
                    var cateList = new String[]{
                            "fashion",
                            "lawn-and-garden",
                    };
                    if (!Arrays.asList(cateList).contains(categoryId)) {
                        continue;
                    }
                } else {
                    categoryId = idStr;
                    var parentIdStr = hrefArr[5];
                    var parentIdArr = parentIdStr.split("zg_bs_nav_");
                    var parentIdStr2 = parentIdArr[1];
                    var parentIdArr2 = parentIdStr2.split("_");
                    if (parentIdArr2.length == 2) {
                        parentId = parentIdArr2[0];
                    }
                    if (parentIdArr2.length == 3) {
                        parentId = parentIdArr2[2];
                    }
                }

                AmazonCategory oldCategory = amazonCategoryService.getByMarketplaceIdAndCategoryId(marketplaceId, categoryId);
                if (oldCategory == null) {
                    var amazonCategory = new AmazonCategory();
                    amazonCategory.setMarketplaceId(marketplaceId);
                    amazonCategory.setCategoryId(categoryId);
                    amazonCategory.setParentId(parentId);
                    amazonCategory.setAncestorsId(ancestorsCategoryId);
                    amazonCategory.setName(categoryName);
                    amazonCategory.setUrl(link);
                    amazonCategory.setTraversalStatus(TraversalStatus.Pending);
                    amazonCategoryService.save(amazonCategory);
                }
                saveSubCategories(link);
            }
        }
    }

    public void fetchBestSellerCategoryAsins(String parentCategoryUrl) throws InterruptedException {

        fetchBestSellerOnePageAsins(parentCategoryUrl);
    }

    private void fetchBestSellerOnePageAsins(String pageUrl) throws InterruptedException {
        var pageNum = getBestSellerPageNumFromUrl(pageUrl);
        if (pageNum > 4) {
            return;
        }
        var marketplaceId = getMarketplaceIdFromUrl(pageUrl);

        var html= resterEngine.getStringResponse(pageUrl);
        Document doc = Jsoup.parse(html);

        Thread.sleep(500);

//        var dataClientDiv = driverEngine.getExecutor().getByClassName("p13n-desktop-grid");
        var dataClientDiv = doc.getElementsByClass("p13n-desktop-grid").get(0);
        var jsonDataListStr = dataClientDiv.attr("data-client-recs-list");
        var array = JSONArray.parseArray(jsonDataListStr);


        var bestSellerCategoryNameSelector ="#CardInstanceLKUIrIElcjdrmh1uLuy_nQ > div._cDEzb_card-title_2sYgw > h1";
        var bestSellerCategoryName = doc.select(bestSellerCategoryNameSelector).text().split("Best Sellers in ")[1];


        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            log.info("正在处理第" + pageNum * (i + 1) + "个商品，共" + pageNum * array.size() + "个商品");
            var jsonObject = array.get(i);
            var url = "";
            var asin = "";
            try {
                // 获取jsonObject的id字段值
                asin = jsonObject.toString();
                var asinArr = asin.split("asin\":\"");
                asin = asinArr[1];
                asinArr = asin.split("\"");
                asin = asinArr[0];

                if(asin==null||asin.equals("")){
                    log.info("获取asin失败，跳过.");
                    continue;
                }

                var one = amazonSelectionService.getOneByMarketplaceIdAndAsin(marketplaceId, asin);
                if (one != null) {
                    log.info("此商品已经存在，跳过.");
                    continue;
                }

                var myUrl = new URL(url);
                var host = myUrl.getHost();
                url = "https://" + host + "/dp/" + asin;
            } catch (Exception ex) {
                log.error("获取Asin信息失败，跳过.");
                continue;
            }
            urlList.add(url);
        }

        log.info(bestSellerCategoryName + ": 开始去产品页拉取第" + pageNum + "页的商品，本页共" + urlList.size() + "个商品");
        for (int i = 0; i < urlList.size(); i++) {
            log.info(bestSellerCategoryName + ": 正在处理第" + (i + 1) + "个商品，共" + urlList.size() + "个商品");
            var url = urlList.get(i);
            fetchOneAsin(url, pageUrl);
            Thread.sleep(1000);
        }
        try {
            var nextPageUrl = "";
            if (pageNum == 1) {
                nextPageUrl = pageUrl + "?&pg=2";
            } else {
                nextPageUrl = pageUrl.replace("&pg=" + pageNum, "&pg=" + (pageNum + 1));
            }
            fetchBestSellerOnePageAsins(nextPageUrl);
        } catch (Exception ex) {
            log.info("没有下一页了" + ex.getMessage());
        }
    }

    private void fetchFollowBuddyAsins(String buddyHomeUrl) throws InterruptedException, MalformedURLException {
        var marketplaceId = getMarketplaceIdFromUrl(buddyHomeUrl);
        var sellerId = getSellerIdFromUrl(buddyHomeUrl);

        var seller = amazonSellerService.getByMarketplaceIdAndSellerId(marketplaceId, sellerId);
        if (seller == null) {
            seller = new AmazonSeller();
            seller.setMarketplaceId(marketplaceId);
            seller.setSellerId(sellerId);
            seller.setFollowSeller(true);
            seller.setHomePageUrl(buddyHomeUrl);
        }
        var html= resterEngine.getStringResponse(buddyHomeUrl);
        Document doc = Jsoup.parse(html);

        Thread.sleep(500);
        var sellerName = doc.select("#seller-name").text();
        seller.setName(sellerName);


        var sellerStoreFrontUrl = doc.select("#seller-info-storefront-link a").attr("href");
        var myUrl = new URL(buddyHomeUrl);
        var host = myUrl.getHost();
        sellerStoreFrontUrl = "https://" + host + sellerStoreFrontUrl;
        seller.setStoreFrontUrl(sellerStoreFrontUrl);

//        html =resterEngine.getStringResponse(sellerStoreFrontUrl);
//        doc = Jsoup.parse(html);
        doc=resterEngine.getJSedHtmlDocument(sellerStoreFrontUrl);
        var resultInfoBar = doc.getElementsByClass("s-desktop-toolbar");
        var resultInfoBarText = resultInfoBar.text();
        var resultInfoBarTextArr = resultInfoBarText.split(" ");
        var totalProductNum = resultInfoBarTextArr[2];
        seller.setLastProductNum(Integer.parseInt(totalProductNum));

        fetchBuddyPageByPage(sellerStoreFrontUrl);

        seller.setLastFetchTime(new Date());
        amazonSellerService.save(seller);
    }

    private void fetchBuddyPageByPage( String sellerStoreFrontUrl) throws MalformedURLException, InterruptedException {
        Document doc;
        String html;
        html =resterEngine.getStringResponse(sellerStoreFrontUrl);
        doc = Jsoup.parse(html);

        var myUrl = new URL(sellerStoreFrontUrl);
        var host = myUrl.getHost();

        var asinCardList = doc.getElementsByClass("s-asin");
        for (int i = 0; i < asinCardList.size(); i++) {
            var asinCard = asinCardList.get(i);
            var a=asinCard.getElementsByTag("a").get(0);
            var url= "https://" + host + a.attr("href");
            fetchOneAsin(url, sellerStoreFrontUrl);
            Thread.sleep(1000);
        }

        var nextPageBtn = doc.getElementsByClass("s-pagination-next");
        var tag = nextPageBtn.get(0).tagName();
        if(tag.equals("a")) {
            var nextPageUrl = "https://" + host + nextPageBtn.get(0).attr("href");
            fetchBuddyPageByPage(nextPageUrl);
        }
    }

    private String getSellerIdFromUrl(String pageUrl) {
        // pageUrl like:https://www.amazon.sg/sp?marketplaceID=A19VAU5U5O7RUS&seller=A2JCL5BQO3UZK7&isAmazonFulfilled=1&ref_=dp_merchant_link&asin=B07YSVJ16T
        // pageUrl 2 like:https://www.amazon.sg/Certified-Kate-Stimulate-Eyelashes-Treatment/dp/B01NALN8Q9/ref=ice_ac_b_dpb?m=A2JCL5BQO3UZK7&marketplaceID=A19VAU5U5O7RUS&qid=1684501795&s=merchant-items&sr=1-1
        var sellerId = "";
        var sellerIdArr = pageUrl.split("seller=");
        if (sellerIdArr.length > 1) {
            sellerId = sellerIdArr[1];
            sellerIdArr = sellerId.split("&");
            sellerId = sellerIdArr[0];
        }
        else{
            sellerIdArr = pageUrl.split("m=");
            if (sellerIdArr.length > 1) {
                sellerId = sellerIdArr[1];
                sellerIdArr = sellerId.split("&");
                sellerId = sellerIdArr[0];
            }
            else{
                sellerId="0";
            }
        }
        return sellerId;
    }

    public void fetchSearchAsins(String searchUrl, String searchKey) {
     var html= resterEngine.getStringResponse(searchUrl);
     Document doc = Jsoup.parse(html);

        Elements links;
        var filteredLinks = new ArrayList<String>();
        try {
//            links = driverEngine.getExecutor().listByCssSelector(".a-link-normal.s-underline-text.s-underline-link-text.s-link-style.a-text-normal");
            links = doc.select(".a-link-normal.s-underline-text.s-underline-link-text.s-link-style.a-text-normal");
            //新建一个new list

            //只取第偶数个
            for (int i = 0; i < links.size(); i++) {
                if (i % 2 == 0) {
                    filteredLinks.add(links.get(i).attr("href"));
                }
            }

            var l = links.size();
            var l2 = filteredLinks.size();
            log.info("搜索关键词：" + searchKey + " " + searchUrl + " 找到商品：" + l2 + "个.");
        } catch (Exception ex) {
            log.info("搜索关键词：" + searchKey + " 没有找到商品.");
            return;
        }

        for (var link : filteredLinks) {
            log.info("开始抓取Asin：" + link);
//            var url = "https://www.amazon.sg"+ link.getAttribute("href");
            fetchOneAsin(link, searchKey);
        }
    }

    //q:如何添加默认形参
    //a:在形参后面加上=默认值
    public void fetchOneAsin(String link, String parentUrl) {
        var marketplaceId = getMarketplaceIdFromUrl(link);
        GatherEntrance entrance = getEntranceFromUrl(parentUrl);

        var url = link;
        try {
            Thread.sleep(1000);
//                driverEngine.getExecutor().openUrlInNewTab(url);
//            driverEngine.getDriver().get(url);
            var html= resterEngine.getStringResponse(url);
            Document doc = Jsoup.parse(html);
            Thread.sleep(1888);

//            var asinInput = driverEngine.getExecutor().getById("ASIN");
            var asinInput = doc.getElementById("ASIN");
            var asin = asinInput.attr("value");
            var one = amazonSelectionService.getOneByMarketplaceIdAndAsin(marketplaceId, asin);
            if (one != null) {
                log.info("此商品已经存在，跳过.");
                return;
            }

            var selection = new AmazonSelection();
            selection.setMarketplaceId(marketplaceId);
            selection.setAsin(asin);
            selection.setUrl(url);

            var sellerId = getSellerIdFromUrl(parentUrl);
            selection.setSellerId(sellerId);

//            var priceSpan = driverEngine.getExecutor().getByCssSelector(".a-price.aok-align-center");
            var priceSpan = doc.select(".a-price.aok-align-center").first();
            var price = priceSpan.text();
            var priceFormat = price.replace("S$", "").replace("\n", ".");
            var priceNum = Double.parseDouble(priceFormat);
            if (priceNum > 60) {
                log.info("此商品价格大于60新币，跳过.");
                selection.setPrice(priceNum + "");
                selection.setConfirmSell(true);
                selection.setConfirmSupplier(true);
                selection.setHasSupplier(false);
                amazonSelectionService.save(selection);
                return;
            }

//            var html = driverEngine.getDriver().getPageSource();
            if (html.contains("There are no customer ratings") || html.contains("No customer reviews")) {
                log.info("此商品没有评论，跳过.");
                selection.setPrice(priceNum + "");
                selection.setConfirmSell(true);
                selection.setConfirmSupplier(true);
                selection.setHasSupplier(false);
                amazonSelectionService.save(selection);
                return;
            }
            if (html.contains("There are 0 reviews and 0 ratings from")) {
                log.info("此商品没有来自该国家的评论，跳过.");
                selection.setPrice(priceNum + "");
                selection.setConfirmSell(true);
                selection.setConfirmSupplier(true);
                selection.setHasSupplier(false);
                amazonSelectionService.save(selection);
                return;
            }

            var categoryId = "";
            var categoryName = "";
            var searchKey = "";
            try {
//                wayFindingDiv = driverEngine.getExecutor().getById("wayfinding-breadcrumbs-container");
                var wayFindingDiv = doc.getElementById("wayfinding-breadcrumbs-container");
//                var links = driverEngine.getExecutor().listByRelativeXpath(wayFindingDiv, ".//a[@class='a-link-normal a-color-tertiary']");
                var links = wayFindingDiv.select(".a-link-normal.a-color-tertiary");
                //取最后一个link
                var leafCategoryLink = links.get(links.size() - 1);
//                var href = leafCategoryLink.getAttribute("href");
                var href = leafCategoryLink.attr("href");
                var paramArr = href.split("&");
                for (var param : paramArr) {
                    if (param.startsWith("node=")) {
                        categoryId = param.replace("node=", "");
                        break;
                    }
                }

                categoryName = leafCategoryLink.text();

                if (entrance == GatherEntrance.BestSeller) {
                    searchKey = categoryName;
                }
                if (entrance == GatherEntrance.Search) {
                    searchKey = getSearchKeyFromSearchUrl(parentUrl);
                }
            } catch (Exception ex) {
                categoryId = getCategoryIdFromBestSellerUrl(parentUrl);
                var category = amazonCategoryService.getByMarketplaceIdAndCategoryId(marketplaceId, categoryId);
                if (category != null) {
                    categoryName = category.getName();
                    searchKey = categoryName;
                }
            }
            String picUrl = "";
            try{
                var picDiv = doc.getElementById("imgTagWrapperId");
                var picImg= picDiv.select("img").first();
                picUrl = picImg.attr("src");
            }catch (Exception ex){
                log.info("获取Asin图片失败");
            }

            selection.setCategoryId(categoryId);
            selection.setGatherEntrance(entrance);
            selection.setPrice(priceNum + "");
            selection.setSearchKey(searchKey);
            selection.setConfirmSell(false);
            selection.setConfirmSupplier(false);
            selection.setHasSupplier(false);
            selection.setPicUrl(picUrl);
            amazonSelectionService.save(selection);
            Thread.sleep(1000);
        } catch (Exception exx) {
            log.info("抓取fetchOneAsin过程出错.");
            log.info(exx.getMessage());
        }
    }

    private String getSearchKeyFromSearchUrl(String parentUrl) {
        var paramArr = parentUrl.split("/s?k=");
        var param = paramArr[1];
        var paramArr2 = param.split("&");
        return paramArr2[0];
    }

    private Integer getBestSellerPageNumFromUrl(String pageUrl) {
        var paramArr = pageUrl.split("&");
        for (var param : paramArr) {
            if (param.startsWith("pg=")) {
                var pageNum = param.replace("pg=", "");
                return Integer.parseInt(pageNum);
            }
        }
        return 1;
    }

    private GatherEntrance getEntranceFromUrl(String url) {
        if (url.contains("/gp/bestsellers")) {
            return GatherEntrance.BestSeller;
        }
        if (url.contains("/s?k=")) {
            return GatherEntrance.Search;
        }
        if (url.contains("/s?bbn=") || url.contains("/s?rh=") || url.contains("/b/ref=")) {
            return GatherEntrance.Category;
        }
        return GatherEntrance.Search;
    }

    private String getCategoryIdFromBestSellerUrl(String url) {
        var paramArr = url.split("/gp/bestsellers/");
        var param = paramArr[1];
        if (param.startsWith("ref=")) {
            return "Root";
        }
        var paramArr2 = param.split("/");
        if (paramArr2.length == 2) {
            return paramArr2[0];
        }
        if (paramArr2.length == 3) {
            return paramArr2[1];
        }
        return "";
    }

    public String getExecutorName(String url) {
        return "AmazonSelection" + "__" + url;
    }

    public String getBestSellerUrl(String countryCode, String category) {
        switch (countryCode) {
            case "US":
                return "https://www.amazon.com/gp/bestsellers/" + category;
            case "UK":
                return "https://www.amazon.co.uk/gp/bestsellers/" + category;
            case "BR":
                return "https://www.amazon.com.br/gp/bestsellers/" + category;
            case "CA":
                return "https://www.amazon.ca/gp/bestsellers/" + category;
            case "AU":
                return "https://www.amazon.com.au/gp/bestsellers/" + category;
            case "SG":
                return "https://www.amazon.sg/gp/bestsellers/" + category;
            default:
                return "https://www.amazon.com/gp/bestsellers/" + category;
        }
    }

    public String getMarketplaceIdFromUrl(String url) {
        if (url.contains("amazon.co.uk")) {
            return "A1F83G8C2ARO7P";
        }
        if (url.contains("amazon.com.br")) {
            return "A2Q3Y263D00KWC";
        }
        if (url.contains("amazon.ca")) {
            return "A2EUQ1WTGCTBG2";
        }
        if (url.contains("amazon.com.au")) {
            return "A39IBJ37TRP1C6";
        }
        if (url.contains("amazon.sg")) {
            return "A19VAU5U5O7RUS";
        }
        return "ATVPDKIKX0DER";
    }



//    protected String fetchHtml(String url) throws IOException {
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            HttpEntity<String> entity = new HttpEntity<>(resterEngine.getBotHeaders());
//            HttpEntity<byte[]> response = restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    entity,
//                    byte[].class
//            );
//
//            byte[] data = RestTemplateHelper.unGZip(new ByteArrayInputStream(Objects.requireNonNull(response.getBody())));
//
//            return new String(data, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
}
