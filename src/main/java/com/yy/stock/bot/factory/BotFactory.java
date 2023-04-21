package com.yy.stock.bot.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.adaptor.seleniumgrid.model.GridStatusResponseModel;
import com.yy.stock.bot.Bot;
import com.yy.stock.bot.engine.core.BotStatus;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class BotFactory {
    @Value("${bot.gridhub.statusUrl}")
    //q:这个配置为什么获取不到
    //a:因为这个配置是在bootstrap-prod.yml中，而不是application.yml中
    public String statusUrl;
    private List<Bot> runningBotPool;

    public BotFactory() {
        runningBotPool = new ArrayList<>();
    }

    public Bot getRegisterBot(Platform platform) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, MalformedURLException, JsonProcessingException {
        var className = platform.getBotBean();
        Class<?> clazz = Class.forName(className);
        //通过反射获取类的实例
        var coreEngine = (CoreEngine) clazz.getConstructor().newInstance();
//        var coreEngine = (PlugBaseEngine) SpringUtil.getBean(clazz);
//        coreEngine.init(buyerAccount);
        var bot = new Bot(coreEngine);
        return bot;
    }

    public Bot getBot(BuyerAccount buyerAccount) throws MalformedURLException, JsonProcessingException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Bot bot : runningBotPool) {
            if (bot.getBotBuyer().getId().equals(buyerAccount.getId())) {
                if (!bot.testAvailable()) {
                    runningBotPool.remove(bot);
                    bot.bye();
                    return getBot(buyerAccount);
                } else {
                    return bot;
                }
            }
        }
        if (getEmptySlotsCount() == 0) {
            throw new RuntimeException("No empty slots in grid hub");
        }
        var className = buyerAccount.getPlatform().getBotBean();
        Class<?> clazz = Class.forName(className);
        var coreEngine = (CoreEngine) clazz.getConstructor().newInstance();
        var bot = new Bot(coreEngine, buyerAccount);
        runningBotPool.add(bot);
        return bot;
    }

    public void keepSessionsAlive() {
        for (Bot bot : runningBotPool) {
            try {
                if (bot.getBotStatus() == BotStatus.idle) {
                    bot.keepSessionAlive();
                }
            } catch (Exception e) {
                log.info("保活任务出错，移除bot，并关闭session");
                runningBotPool.remove(bot);
                bot.bye();
            }
        }
    }

    public void removeBot(BuyerAccount buyerAccount) {
        for (Bot bot : runningBotPool) {
            if (bot.getBotBuyer().getId().equals(buyerAccount.getId())) {
                runningBotPool.remove(bot);
                return;
            }
        }
    }

    public int getEmptySlotsCount() {
        var restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        HttpEntity<GridStatusResponseModel> response = restTemplate.exchange(statusUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
        });
        var gridStatusResponseModel = response.getBody();
        var countSlots = gridStatusResponseModel.getValue().getNodes().stream().mapToInt(node -> node.getSlots().size()).sum();
        var countNotNullSessionsThatSessionInSlots = gridStatusResponseModel.getValue().getNodes().stream().mapToInt(node -> (int) node.getSlots().stream().filter(slot -> slot.getSession() != null).count()).sum();
        return countSlots - countNotNullSessionsThatSessionInSlots;
    }

    public int getSlotsPoolSize() {
        var restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        HttpEntity<GridStatusResponseModel> response = restTemplate.exchange(statusUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
        });
        var gridStatusResponseModel = response.getBody();
        return gridStatusResponseModel.getValue().getNodes().stream().mapToInt(node -> node.getSlots().size()).sum();
    }
}