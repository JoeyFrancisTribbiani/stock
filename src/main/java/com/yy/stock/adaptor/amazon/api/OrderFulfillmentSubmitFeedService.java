package com.yy.stock.adaptor.amazon.api;

import com.yy.stock.adaptor.amazon.dto.AmazonAuthority;
import com.yy.stock.adaptor.amazon.dto.SubmitFeedRequest;
import com.yy.stock.adaptor.amazon.dto.UserInfo;
import com.yy.stock.adaptor.amazon.service.AmazonAuthService;
import com.yy.stock.entity.StockStatus;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
@Service
public class OrderFulfillmentSubmitFeedService {
    @Autowired
    private AmazonAuthService amazonAuthService;
    @Autowired
    private AmazonClientOneFeign amazonClientOneFeign;

    public void submit(StockStatus stockStatus) throws ParserConfigurationException, DatatypeConfigurationException, IOException {
////请参考 上传发运同步报文.xml 进行dom4j//创建发货同步报文
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//
//        org.w3c.dom.Document document = builder.newDocument();
        var auth = amazonAuthService.getById(stockStatus.getAmazonAuthId().longValue());
        var sellerId = auth.getSellerid();

        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        Element amazonEnvelope = document.addElement("AmazonEnvelope");
        amazonEnvelope.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        amazonEnvelope.addAttribute("xsi:noNamespaceSchemaLocation", "amznenvelope.xsd");
        Element header = amazonEnvelope.addElement("Header");
        Element documentVersion = header.addElement("DocumentVersion");
        documentVersion.setText("1.01");
        Element merchantIdentifier = header.addElement("MerchantIdentifier");
        merchantIdentifier.setText(sellerId);//卖家编号
        Element messageType = amazonEnvelope.addElement("MessageType");
        messageType.setText("OrderFulfillment");
        Element message = amazonEnvelope.addElement("Message");
        //Element messageID = message.addElement("MessageID");
        //messageID.setText(arg0);//Seller-supplied unique identifier for the shipment (not used by Amazon)
        //name.setText("ddvip"); //添加Text值；例：<a>abc</a>
        //name.setAttributeValue("sa", "sa"); //添加属性；例：<a item="item"></a>
        Element orderFulfillment = message.addElement("OrderFulfillment");

        Element amazonOrderID = orderFulfillment.addElement("AmazonOrderID");
        amazonOrderID.setText(stockStatus.getAmazonOrderId());//亚马逊订单号

        Element fulfillmentDate = orderFulfillment.addElement("FulfillmentDate");
//        Calendar fillCal = Calendar.getInstance();
//        fillCal.setTime(new Date());

        GregorianCalendar gcal = new GregorianCalendar();
        Date date = new Date();
        gcal.setTime(date);
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
//        XMLGregorianCalendar xmlGregorianCalendar = CommonUtil.toXmlCalendar(fillCal);
        fulfillmentDate.setText(xmlGregorianCalendar.toString());

        Element fulfillmentData = orderFulfillment.addElement("FulfillmentData");
        Element carrierCode = fulfillmentData.addElement("CarrierCode");
        var deliverySupplierId = "CainiaoGlobal";
        carrierCode.setText(deliverySupplierId);
        Element shippingMethod = fulfillmentData.addElement("ShippingMethod");
        shippingMethod.setText("0");//发货方式  0=自己联系、10=仓发货, 默认值为10
        Element shipperTrackingNumber = fulfillmentData.addElement("ShipperTrackingNumber");
        shipperTrackingNumber.setText(stockStatus.getShipmentTrackNumber());//运单号

        //发运明细
//        if (synchStateInfo.getConsignInfo() != null && synchStateInfo.getConsignInfo().size() > 0) {
//            for (ConsignInfo consignInfo : synchStateInfo.getConsignInfo()) {
        Element item = orderFulfillment.addElement("Item");
        Element merchantOrderItemID = item.addElement("MerchantOrderItemID");
        merchantOrderItemID.setText(stockStatus.getAmazonSku());//商品的卖家 SKU。P110118110101010395
        Element quantity = item.addElement("Quantity");
        quantity.setText(stockStatus.getQuantity().toString());
//            }
//        }

//        SubmitFeedRequest request = new SubmitFeedRequest();
//        request.setMerchant(shopInfo.getSessionkey());//商户编号
//        final IdList marketplaces = new IdList(Arrays.asList(AMAZON_CHINA_SHOP_CODE));
//        request.setMarketplaceIdList(marketplaces);
//        request.setFeedType("_POST_ORDER_FULFILLMENT_DATA_");
        //http://docs.developer.amazonservices.com/zh_CN/feeds/Feeds_FeedType.html
        //订单配送确认上传数据 	_POST_ORDER_FULFILLMENT_DATA_
//        request.setFeedContent(new ByteArrayInputStream(document.asXML().getBytes("UTF-8")));
        SubmitFeedRequest submitFeedRequest = new SubmitFeedRequest();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(baos, format);
        writer.write(document);
        log.info(baos.toString());


        submitFeedRequest.setContent(baos);
        submitFeedRequest.setName("fulfillment_" + stockStatus.getOrderItemId());
        AmazonAuthority amazonAuthority = new AmazonAuthority();
        BeanUtils.copyProperties(auth, amazonAuthority);
        submitFeedRequest.setAmazonAuthority(amazonAuthority);
        var user = new UserInfo();
        user.setId("888888");
        submitFeedRequest.setUser(user);
        submitFeedRequest.setFeedType("_POST_ORDER_FULFILLMENT_DATA_");

        amazonClientOneFeign.submitFeed(submitFeedRequest);

    }
}
