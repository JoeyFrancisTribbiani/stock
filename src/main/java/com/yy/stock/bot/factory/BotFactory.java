package com.yy.stock.bot.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.adaptor.seleniumgrid.model.GridStatusResponseModel;
import com.yy.stock.bot.Bot;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.entity.BuyerAccount;
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
public class BotFactory {
    @Value("${bot.gridhub.statusUrl}")
    //q:这个配置为什么获取不到
    //a:因为这个配置是在bootstrap-prod.yml中，而不是application.yml中
    public String statusUrl;
    private List<Bot> runningBotPool;

    public BotFactory() {
        runningBotPool = new ArrayList<>();
    }

    public Bot getBot(BuyerAccount buyerAccount) throws MalformedURLException, JsonProcessingException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Bot bot : runningBotPool) {
            if (bot.getBotBuyer().getId().equals(buyerAccount.getId())) {
                return bot;
            }
        }
        if (getEmptySlotsCount() == 0) {
            throw new RuntimeException("No empty slots in grid hub");
        }
        var className = buyerAccount.getPlatform().getBotBean();
        //通过jdk的反射获取类
//        Class<?> clazz = Class.forName("com.yy.stock.bot.factory." + className);
        Class<?> clazz = Class.forName(className);
        //通过反射获取类的实例
        var coreEngine = (CoreEngine) clazz.getConstructor().newInstance();
//        var coreEngine = (PlugBaseEngine) SpringUtil.getBean(clazz);
//        coreEngine.init(buyerAccount);
        var bot = new Bot(coreEngine, buyerAccount);
        runningBotPool.add(bot);
        return bot;
    }

    public void keepSessionsAlive() {
        for (Bot bot : runningBotPool) {
            try {
                bot.keepSessionAlive();
            } catch (Exception e) {
                e.printStackTrace();
                runningBotPool.remove(bot);
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