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
public class InventorySubmitFeedService {
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
////请参考 上传发运同步报文.xml 进行dom4j//创建发货同步报文
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//
//        org.w3c.dom.Document document = builder.newDocument();
        var auth = amazonAuthService.getById(selectionHasFollow.getAmazonAuthId().longValue());
        var sellerId = auth.getSellerid();
        var amazonSelection = amazonSelectionService.getById(selectionHasFollow.getAmazonSelectionId());
        var marketplace = marketplaceService.getById(amazonSelection.getMarketplaceId());

        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        Element amazonEnvelope = document.addElement("AmazonEnvelope");
//        amazonEnvelope.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        amazonEnvelope.addAttribute("xsi:noNamespaceSchemaLocation", "amznenvelope.xsd");
        Element header = amazonEnvelope.addElement("Header");
        Element documentVersion = header.addElement("DocumentVersion");
        documentVersion.setText("1.01");
        Element merchantIdentifier = header.addElement("MerchantIdentifier");
        merchantIdentifier.setText(sellerId);//卖家编号
        Element messageType = amazonEnvelope.addElement("MessageType");
        messageType.setText("Inventory");
//        Element purgeAndReplace = amazonEnvelope.addElement("PurgeAndReplace");
//        purgeAndReplace.setText("true");
        Element message = amazonEnvelope.addElement("Message");
        Element msgid = message.addElement("MessageID");
        msgid.setText("1");
        Element operationType = message.addElement("OperationType");
        operationType.setText("Update");
        Element inventory = message.addElement("Inventory");
        Element sku = inventory.addElement("SKU");
        var skuValue = productInfoService.getBySelection(selectionHasFollow).getSku();
        sku.setText(skuValue);//商品编号
        Element fulfillmentCenterID = inventory.addElement("FulfillmentCenterID");
        fulfillmentCenterID.setText("DEFAULT");//发货中心编号
        Element quantity = inventory.addElement("Quantity");
        quantity.setText("0");//库存数量

        SubmitFeedRequest submitFeedRequest = new SubmitFeedRequest();
        var xmlStr = document.asXML();

        submitFeedRequest.setContent(xmlStr);
        submitFeedRequest.setName("inventory_" + skuValue );
        AmazonAuthority amazonAuthority = new AmazonAuthority();
        amazonAuthority.setMarketPlace(marketplace);
        amazonAuthority.setShopId(auth.getShopId().toString());
        amazonAuthority.setId(auth.getId().toString());
        submitFeedRequest.setAmazonAuthority(amazonAuthority);
        var user = new UserInfo();
        user.setId("888888");
        submitFeedRequest.setUser(user);
//        submitFeedRequest.setFeedType("POST_ORDER_FULFILLMENT_DATA");
        submitFeedRequest.setFeedType("POST_INVENTORY_AVAILABILITY_DATA");

        amazonClientOneFeign.submitFeed(submitFeedRequest);

    }

}