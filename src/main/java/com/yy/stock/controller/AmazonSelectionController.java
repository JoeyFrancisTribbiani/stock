package com.yy.stock.controller;

import com.yy.stock.adaptor.amazon.api.PriceSubmitFeedService;
import com.yy.stock.bot.engine.core.BotStatus;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.result.Result;
import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.entity.AmazonSelectionHasFollow;
import com.yy.stock.entity.AmazonSeller;
import com.yy.stock.service.AmazonSelectionHasFollowService;
import com.yy.stock.service.AmazonSelectionService;
import com.yy.stock.vo.AmazonSelectionQueryVO;
import com.yy.stock.vo.AmazonSelectionStatusVo;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/amazonSelection")
public class AmazonSelectionController {
    @Autowired
    private BotFactory botFactory;
    @Autowired
    private AmazonSelectionService amazonSelectionService;
    @Autowired
    private AmazonSelectionHasFollowService amazonSelectionHasFollowService;
    @Autowired
    private PriceSubmitFeedService priceSubmitFeedService;

    @PostMapping("/list")
    public Result<Page<AmazonSelectionStatusVo>> query(@Valid @RequestBody AmazonSelectionQueryVO vo) {
        if (vo.isHasFollowSell()) {
            List<AmazonSelectionHasFollow> hasFollowSellList = amazonSelectionHasFollowService.getAllHasFollowed(vo.getAmazonAuthId());
            // 根据上面列表获取AmazonSelection list
            List<AmazonSelection> amazonSelectionList = amazonSelectionService.getBySelectionIds(hasFollowSellList.stream().map(AmazonSelectionHasFollow::getAmazonSelectionId).toList());
            // 根据上面列表获取AmazonSelectionStatusVo Page
            List<AmazonSelectionStatusVo> statusVoList = amazonSelectionList.stream().map(x -> {
                AmazonSelectionStatusVo newVo = new AmazonSelectionStatusVo();
                BeanUtils.copyProperties(x, newVo);
                newVo.setAmazonAuthId(vo.getAmazonAuthId());
                AmazonSelectionHasFollow hasFollowSell = hasFollowSellList.stream().filter(y -> y.getAmazonSelectionId().equals(x.getId())).findFirst().orElse(null);
                newVo.setHasFollowSell(hasFollowSell != null && hasFollowSell.getHasFollowSell() != null && hasFollowSell.getHasFollowSell());
                newVo.setFollowSellSwitch(hasFollowSell != null && hasFollowSell.getFollowSellSwitch() != null && hasFollowSell.getFollowSellSwitch());
                newVo.setWinPurchaseButton(hasFollowSell != null && hasFollowSell.getWinPurchaseButton() != null && hasFollowSell.getWinPurchaseButton());
                newVo.setSetPrice(hasFollowSell != null ? hasFollowSell.getSetPrice() : null);
                return newVo;
            }).collect(Collectors.toList());
            return Result.success(new PageImpl<>(statusVoList, vo.toPageRequest(), statusVoList.size()));

        }
        var pageble = amazonSelectionService.query(vo);
        var selectionIds = pageble.getContent().stream().map(AmazonSelection::getId).toList();
        List<AmazonSelectionHasFollow> hasFollowSellList = amazonSelectionHasFollowService.getBySelectionIds(vo.getAmazonAuthId(), selectionIds);
        Page<AmazonSelectionStatusVo> statusVoPage = new PageImpl<>(pageble.getContent().stream().map(x -> {
            AmazonSelectionStatusVo newVo = new AmazonSelectionStatusVo();
            BeanUtils.copyProperties(x, newVo);
            newVo.setAmazonAuthId(vo.getAmazonAuthId());
            AmazonSelectionHasFollow hasFollowSell;
            if (vo.getAmazonAuthId() != null) {
                hasFollowSell = hasFollowSellList.stream().filter(y -> Objects.equals(y.getAmazonAuthId(), vo.getAmazonAuthId()) && y.getAmazonSelectionId().equals(x.getId())).findFirst().orElse(null);
            } else {
                hasFollowSell = hasFollowSellList.stream().filter(y -> y.getAmazonSelectionId().equals(x.getId())).findFirst().orElse(null);
            }
            if (hasFollowSell != null) {
                newVo.setHasFollowSell(hasFollowSell.getHasFollowSell() != null && hasFollowSell.getHasFollowSell());
                newVo.setFollowSellSwitch(hasFollowSell.getFollowSellSwitch() != null && hasFollowSell.getFollowSellSwitch());
                newVo.setWinPurchaseButton(hasFollowSell.getWinPurchaseButton() != null && hasFollowSell.getWinPurchaseButton());
                newVo.setSetPrice(hasFollowSell.getSetPrice());
            } else {
                newVo.setHasFollowSell(false);
                newVo.setFollowSellSwitch(false);
                newVo.setWinPurchaseButton(false);
            }
            return newVo;
        }).collect(Collectors.toList()), pageble.getPageable(), pageble.getTotalElements());
        return Result.success(statusVoPage);
    }

    @PostMapping("/save")
    public Result<String> save(@Valid @RequestBody AmazonSelectionStatusVo vo) {
        var sameAsin = amazonSelectionService.getOneByMarketplaceIdAndAsin(vo.getMarketplaceId(), vo.getAsin());
        sameAsin.setConfirmSupplier(vo.getConfirmSupplier());
        sameAsin.setHasSupplier(vo.getHasSupplier());
        sameAsin.setHasFavorite(vo.getHasFavorite());
        amazonSelectionService.save(sameAsin);
        if (vo.getAmazonAuthId() == null & vo.getFollowSellSwitch()) {
            return Result.failed("authId为空，请先选择店铺！");
        }
        if (vo.getAmazonAuthId() == null) {
            return Result.success(sameAsin.getAsin());
        }

        var hasFollowSell = amazonSelectionHasFollowService.getBySelectionId(vo.getAmazonAuthId(), sameAsin.getId());
        if (hasFollowSell == null) {
            hasFollowSell = new AmazonSelectionHasFollow();
            hasFollowSell.setAmazonAuthId(vo.getAmazonAuthId());
            hasFollowSell.setAmazonSelectionId(sameAsin.getId());
            hasFollowSell.setHasFollowSell(false);
            hasFollowSell.setFollowSellSwitch(vo.getFollowSellSwitch());
            hasFollowSell.setSetPrice("");
            hasFollowSell.setSku(vo.getSku());
        } else {
            hasFollowSell.setFollowSellSwitch(vo.getFollowSellSwitch());
            hasFollowSell.setSetPrice(vo.getSetPrice());
        }
        amazonSelectionHasFollowService.save(hasFollowSell);

        return Result.success(vo.getAsin());
    }


    @PostMapping("/updatePrice")
    public Result<String> updatePrice(@Valid @RequestBody AmazonSelectionStatusVo vo) throws DatatypeConfigurationException, ParserConfigurationException, IOException {
        var sameAsin = amazonSelectionService.getOneByMarketplaceIdAndAsin(vo.getMarketplaceId(), vo.getAsin());
        sameAsin.setConfirmSupplier(vo.getConfirmSupplier());
        sameAsin.setHasSupplier(vo.getHasSupplier());
        amazonSelectionService.save(sameAsin);
        if (vo.getAmazonAuthId() == null) {
            return Result.failed("参数错误，amazonAuthId不能为空");
        }

        var hasFollowSell = amazonSelectionHasFollowService.getBySelectionId(vo.getAmazonAuthId(), sameAsin.getId());
        if (hasFollowSell == null) {
            return Result.failed("参数错误，该产品未跟卖");
        } else {
            hasFollowSell.setSetPrice(vo.getSetPrice());
            amazonSelectionHasFollowService.save(hasFollowSell);
            priceSubmitFeedService.submit(hasFollowSell);
        }

        return Result.success(vo.getAsin());
    }

    @PostMapping("/fetch")
    public Result<String> fetch(@Valid @RequestBody AmazonSeller seller) throws DatatypeConfigurationException, ParserConfigurationException, IOException, MessagingException, InterruptedException {
        var amazonBot = botFactory.getAmazonBot(seller.getHomePageUrl());
        if(amazonBot.getBotStatus()== BotStatus.fetching){
            return Result.success("");
        }
        amazonBot.fetch(seller.getHomePageUrl());
        return Result.success("成功运行抓取任务");
    }
}
