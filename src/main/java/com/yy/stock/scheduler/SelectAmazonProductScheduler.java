package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.bot.engine.driver.DebugChromeDriverEngine;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.service.AmazonSelectionService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private DebugChromeDriverEngine driverEngine;

    @XxlJob(value = "selectAmazonProductJobHandler")
    public void selectAmazonProductXxlJobHandler(String searchKey) throws InterruptedException {
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
        var searchUrl = "https://www.amazon.sg/s?k=" + searchKey;

        int maxPage = 3;
        for (int i = 1; i <= maxPage; i++) {
            searchUrl = searchUrl + "&page=" + i;
            fetchAsin(searchUrl, searchKey);
        }


    }

    private void fetchAsin(String searchUrl, String searchKey) {
        driverEngine.getDriver().get(searchUrl);

        List<WebElement> links;
        try {
            links = driverEngine.getExecutor().listByClassName("a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal");
        } catch (Exception ex) {
            log.info("搜索关键词：" + searchKey + " 没有找到商品.");
            return;
        }

        for (var link : links) {
            log.info("开始抓取Asin：" + link);
            var url = link.getAttribute("href");
            try {
                driverEngine.getDriver().get(url);
                var html = driverEngine.getDriver().getPageSource();
                if (html.contains("There are 0 reviews and 0 ratings from Singapore")) {
                    log.info("此商品没有评论，跳过.");
                    continue;
                }
                var asinInput = driverEngine.getExecutor().getById("ASIN");
                var asin = asinInput.getAttribute("value");
                var priceSpan = driverEngine.getExecutor().getByClassName("a-price aok-align-center");
                var price = priceSpan.getText();
                var selection = new AmazonSelection();
                selection.setAsin(asin);
                selection.setPrice(price);
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
