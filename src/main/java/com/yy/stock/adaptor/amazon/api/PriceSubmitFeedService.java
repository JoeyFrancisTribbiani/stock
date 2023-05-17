package com.yy.stock.adaptor.amazon.api;

import com.yy.stock.adaptor.amazon.dto.AmazonAuthority;
import com.yy.stock.adaptor.amazon.dto.SubmitFeedRequest;
import com.yy.stock.adaptor.amazon.dto.UserInfo;
import com.yy.stock.adaptor.amazon.service.AmazonAuthService;
import com.yy.stock.adaptor.amazon.service.MarketplaceService;
import com.yy.stock.adaptor.amazon.service.ProductInfoService;
import com.yy.stock.entity.AmazonSelectionHasFollow;
import com.yy.stock.service.AmazonSelectionService;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Slf4j
@Service
public class PriceSubmitFeedService {
    @Autowired
    private MarketplaceService marketplaceService;
    @Autowired
    private AmazonAuthService amazonAuthService;
    @Autowired
    private AmazonClientOneFeign amazonClientOneFeign;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private AmazonSelectionService amazonSelectionService;

    public void submit(AmazonSelectionHasFollow selectionHasFollow) throws ParserConfigurationException, DatatypeConfigurationException, IOException {
        //使用spapi feed上传xml，更新商品价格
        var auth = amazonAuthService.getById(selectionHasFollow.getAmazonAuthId().longValue());
        var sellerId = auth.getSellerid();
        var amazonSelection = amazonSelectionService.getById(selectionHasFollow.getAmazonSelectionId());
        var marketplace = marketplaceService.getById(amazonSelection.getMarketplaceId());

        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        Element amazonEnvelope = document.addElement("AmazonEnvelope");
        Element header = amazonEnvelope.addElement("Header");
        Element documentVersion = header.addElement("DocumentVersion");
        documentVersion.setText("1.01");
        Element merchantIdentifier = header.addElement("MerchantIdentifier");
        merchantIdentifier.setText(sellerId);//卖家编号
        Element messageType = amazonEnvelope.addElement("MessageType");
        messageType.setText("Price");
        Element message = amazonEnvelope.addElement("Message");
        Element msgid = message.addElement("MessageID");
        msgid.setText("1");
        Element price = message.addElement("Price");
        Element sku = price.addElement("SKU");
        var skuValue = productInfoService.getBySelection(selectionHasFollow).getSku();
        sku.setText(skuValue);
        Element standardPrice = price.addElement("StandardPrice");
        standardPrice.setText(selectionHasFollow.getSetPrice());
        standardPrice.addAttribute("currency", "SGD");
        Element businessPrice = price.addElement("BusinessPrice");
        businessPrice.setText(amazonSelection.getPrice());


        SubmitFeedRequest submitFeedRequest = new SubmitFeedRequest();
        var xmlStr = document.asXML();

        submitFeedRequest.setContent(xmlStr);
        submitFeedRequest.setName("pricing_" +skuValue+"_"+selectionHasFollow.getSetPrice());
        AmazonAuthority amazonAuthority = new AmazonAuthority();
        amazonAuthority.setMarketPlace(marketplace);
        amazonAuthority.setShopId(auth.getShopId().toString());
        amazonAuthority.setId(auth.getId().toString());
        submitFeedRequest.setAmazonAuthority(amazonAuthority);
        var user = new UserInfo();
        user.setId("888888");
        submitFeedRequest.setUser(user);
//        submitFeedRequest.setFeedType("POST_ORDER_FULFILLMENT_DATA");
//        submitFeedRequest.setFeedType("POST_INVENTORY_AVAILABILITY_DATA");
        submitFeedRequest.setFeedType("POST_PRODUCT_PRICING_DATA");

        amazonClientOneFeign.submitFeed(submitFeedRequest);

    }

}