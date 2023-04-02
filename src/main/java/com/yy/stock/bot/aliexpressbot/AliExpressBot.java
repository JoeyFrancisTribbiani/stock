package com.yy.stock.bot.aliexpressbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.Bot;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressClassSelectors;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressUrls;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressXpaths;
import com.yy.stock.common.util.YamlSourceFactory;
import com.yy.stock.dto.TrackRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@PropertySource(value = "classpath:configs/aliexpress.yml", factory = YamlSourceFactory.class)    // 指定自定义配置文件位置和名称
public abstract class AliExpressBot extends Bot {
    @Autowired
    protected AliExpressXpaths xpaths;
    @Autowired
    protected AliExpressUrls urls;
    @Autowired
    protected AliExpressClassSelectors classSelectors;

    protected AliExpressBot() throws MalformedURLException, JsonProcessingException {
        super();
        log.info("Construct AliExpressBot Instance...");
    }


//    public ShipmentInfo trackOrderByUrl(String url) throws JsonProcessingException {
//        var modules = getCainiaoTrackInfoByUrl(url);
//        var json = new ObjectMapper().writeValueAsString(modules.get(0));
//        return new ShipmentInfo();
//    }

//    public void trackLogisticByAmazonOrderInfo(OrderItemAdaptorInfoDTO order) throws JsonProcessingException, InterruptedException {
//        var stockStatus = stockStatusService.getOrCreateByOrderItemSku(order);
//        trackLogisticByStockStatus(stockStatus);
//    }


    @Override
    public void doTrack(TrackRequest trackRequest) throws IOException, InterruptedException, DatatypeConfigurationException, ParserConfigurationException {
        this.trackRequest = trackRequest;
        var stock = trackRequest.getStockStatus();
        trackLogisticByStockStatus(stock);
    }

}
