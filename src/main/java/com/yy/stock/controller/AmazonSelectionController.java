package com.yy.stock.controller;

import com.yy.stock.common.result.Result;
import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.entity.AmazonSelectionHasFollow;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/amazonSelection")
public class AmazonSelectionController {

    @Autowired
    private AmazonSelectionService amazonSelectionService;
    @Autowired
    private AmazonSelectionHasFollowService amazonSelectionHasFollowService;

    @PostMapping("/list")
    public Result<Page<AmazonSelectionStatusVo>> query(@Valid @RequestBody AmazonSelectionQueryVO vo) {
        var pageble = amazonSelectionService.query(vo);
        var selectionIds = pageble.getContent().stream().map(AmazonSelection::getId).toList();
        List<AmazonSelectionHasFollow> hasFollowSellList = amazonSelectionHasFollowService.getBySelectionIds(vo.getAmazonAuthId(), selectionIds);
        Page<AmazonSelectionStatusVo> statusVoPage = new PageImpl<>(pageble.getContent().stream().map(x -> {
            AmazonSelectionStatusVo newVo = new AmazonSelectionStatusVo();
            BeanUtils.copyProperties(x, newVo);
            newVo.setAmazonAuthId(vo.getAmazonAuthId());
            var hasFollowSell = hasFollowSellList.stream().filter(y -> y.getAmazonSelectionId().equals(x.getId())).findFirst().orElse(null);
            if (hasFollowSell != null) {
                newVo.setHasFollowSell(hasFollowSell.getHasFollowSell());
                newVo.setFollowSellSwitch(hasFollowSell.getFollowSellSwitch());
            }else{
                newVo.setHasFollowSell(false);
                newVo.setFollowSellSwitch(false);
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
        amazonSelectionService.save(sameAsin);
        if(vo.getAmazonAuthId()==null){
            return Result.success(vo.getAsin());
        }

        var hasFollowSell = amazonSelectionHasFollowService.getBySelectionId(vo.getAmazonAuthId(), sameAsin.getId());
        if (hasFollowSell == null) {
            hasFollowSell = new AmazonSelectionHasFollow();
            hasFollowSell.setAmazonAuthId(vo.getAmazonAuthId());
            hasFollowSell.setAmazonSelectionId(sameAsin.getId());
            hasFollowSell.setHasFollowSell(false);
            hasFollowSell.setFollowSellSwitch(false);
            hasFollowSell.setSku(vo.getSku());
        }else {
            hasFollowSell.setFollowSellSwitch(vo.getFollowSellSwitch());
        }
        amazonSelectionHasFollowService.save(hasFollowSell);

        return Result.success(vo.getAsin());
    }
}
