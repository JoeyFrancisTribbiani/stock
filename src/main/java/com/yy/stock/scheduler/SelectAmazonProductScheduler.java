package com.yy.stock.scheduler;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.bot.engine.driver.DebugChromeDriverEngine;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.service.AmazonSelectionService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SelectAmazonProductScheduler {

    @Autowired
    protected BotFactory botFactory;
    @Autowired
    protected RedissonDistributedLocker distributedLocker;
    private boolean isBusy;

    @Autowired
    private AmazonSelectionService amazonSelectionService;

    public DebugChromeDriverEngine driverEngine;

    @XxlJob(value = "selectAmazonProductJobHandler")
    public void selectAmazonProductXxlJobHandler() throws InterruptedException {
        String searchKey= XxlJobHelper.getJobParam();
        if (isBusy()) {
            log.info("选品任务正忙，跳过此次计划.");
            return;
        }
        isBusy = true;
        try {
            schedule(searchKey);
        } catch (Exception ex) {
            log.info("选品任务过程中报错,ex:" + ex.getMessage());
        } finally {
            isBusy = false;
        }
    }

    public void schedule(String searchKey) throws InterruptedException {
        log.info("开始搜索关键词：" + searchKey);
        if (driverEngine == null) {
            driverEngine = new DebugChromeDriverEngine();
        }
        if(searchKey.startsWith("BestSeller")){
            fetchBestSeller(searchKey);
            return;
        }
        var searchUrl = "https://www.amazon.sg/s?k=" + searchKey;

        int maxPage = 3;
        for (int i = 1; i <= maxPage; i++) {
            searchUrl = searchUrl + "&page=" + i;
            fetchAsin(searchUrl, searchKey);
        }
    }

    private void fetchBestSeller(String searchKey) throws InterruptedException {
        var categoryArr = searchKey.split("-");
        if(categoryArr.length <2){
            var searchUrl = "https://www.amazon.sg/gp/bestsellers/";
            driverEngine.getDriver().get(searchUrl);
            Thread.sleep(2000);
            var html = driverEngine.getDriver().getPageSource();
            Document doc = Jsoup.parse(html);
            var categoryItems = doc.getElementsByAttributeValue("role","treeitem");
            for(var item :categoryItems){
                var children=item.children();
                for(var child : children){
                    var tagName = child.tagName();
                    if(tagName.equals("span")){
                        if(!child.text().equals("Any Category"))
                        {

                        }
                    }
                    if(tagName.equals("a")){
                        var link = child.attr("href");
                        var category = child.text();
//                        var navLevelStr = link.split("ref=")[1];
                        var levelStr = "https://www.amazon.sg/gp/bestsellers/baby/ref=zg_bs_nav_0".split("zg_bs_nav_")[1];
                        if(levelStr.equals("0")){
                            continue;
                        }
                    }
                }
            }
        }else {
            var category = categoryArr[1];

            var searchUrl = "https://www.amazon.sg/gp/bestsellers/" + category;
            driverEngine.getDriver().get(searchUrl);


            var dataClientDiv = driverEngine.getExecutor().getByClassName("p13n-desktop-grid");
            var jsonDataListStr = dataClientDiv.getAttribute("data-client-recs-list");
            var array = JSONArray.parseArray(jsonDataListStr);
            for (var item : array) {
                var asin = ((JSONObject) item).getString("id");
                var url = "https://www.amazon.sg/dp/" + asin;
                var one = amazonSelectionService.getOneByAsin(asin);
                if (one.isPresent()) {
                    log.info("此商品已经存在，跳过.");
                    continue;
                }
                var price = ((JSONObject) item).getString("price");
                var priceNum = Double.parseDouble(price);
                if (priceNum > 38) {
                    log.info("此商品价格大于38新币，跳过.");
                    continue;
                }
                var selection = new AmazonSelection();
                selection.setAsin(asin);
                selection.setPrice(priceNum + "");
                selection.setUrl(url);
                selection.setSearchKey(searchKey);
                selection.setMarketplaceId("A19VAU5U5O7RUS");
                amazonSelectionService.save(selection);
            }
        }
    }

    public void fetchAsin(String searchUrl, String searchKey) {
        driverEngine.getDriver().get(searchUrl);

        List<WebElement> links;
        var filteredLinks = new ArrayList<String>();
        try {
            links = driverEngine.getExecutor().listByCssSelector(".a-link-normal.s-underline-text.s-underline-link-text.s-link-style.a-text-normal");
            //新建一个new list

            //只取第偶数个
            for (int i = 0; i < links.size(); i++) {
                if (i % 2 == 0) {
                    filteredLinks.add(links.get(i).getAttribute("href"));
                }
            }

            var l = links.size();
            var l2 = filteredLinks.size();
            log.info("搜索关键词：" + searchKey + " "+ searchUrl +" 找到商品：" + l2 + "个.");
        } catch (Exception ex) {
            log.info("搜索关键词：" + searchKey + " 没有找到商品.");
            return;
        }

        for (var link : filteredLinks) {
            log.info("开始抓取Asin：" + link);
//            var url = "https://www.amazon.sg"+ link.getAttribute("href");
            var url = link;
            try {
                Thread.sleep(1000);
//                driverEngine.getExecutor().openUrlInNewTab(url);
                driverEngine.getDriver().get(url);
                Thread.sleep(8888);
                var html = driverEngine.getDriver().getPageSource();
                if (html.contains("There are no customer ratings")) {
                    log.info("此商品没有评论，跳过.");
                    continue;
                }
                if (html.contains("There are 0 reviews and 0 ratings from Singapore")) {
                    log.info("此商品没有来自新加坡的评论，跳过.");
                    continue;
                }
                var asinInput = driverEngine.getExecutor().getById("ASIN");
                var asin = asinInput.getAttribute("value");
                var one = amazonSelectionService.getOneByAsin(asin);
                if(one.isPresent()){
                    log.info("此商品已经存在，跳过.");
                    continue;
                }
                var priceSpan = driverEngine.getExecutor().getByCssSelector(".a-price.aok-align-center");
                var price = priceSpan.getText();
                var priceFormat = price.replace("S$", "").replace("\n", ".");
                var priceNum = Double.parseDouble(priceFormat);
                if(priceNum>38){
                    log.info("此商品价格大于38新币，跳过.");
                    continue;
                }


                var selection = new AmazonSelection();
                selection.setAsin(asin);
                selection.setPrice(priceNum + "");
                selection.setUrl(url);
                selection.setSearchKey(searchKey);
                selection.setMarketplaceId("A19VAU5U5O7RUS");
                amazonSelectionService.save(selection);
                Thread.sleep(1000);
            } catch (Exception exx) {
                log.info(getExecutorName(url) + " 抓取过程出错.");
            }
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public String getExecutorName(String url) {
        return "AmazonSelection" + "__" + url;
    }
}
