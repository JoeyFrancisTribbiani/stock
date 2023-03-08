package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.OrdersReport;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface OrdersReportRepository extends JpaRepository<OrdersReport, String>, JpaSpecificationExecutor<OrdersReport> {
    public List<OrdersReport> findAllByOrderStatus(String orderStatus);

    @Query(value = "select     t.purchase_date buydate,    t.item_status itemstatus,    t.order_status orderstatus,    t.amazon_order_id orderid,    g.id groupid,\tt.fulfillment_channel channel,\tt.item_price orderprice,\tifnull((t.item_price)/(t.quantity),0) itemprice,\tt.quantity,\tifnull(p.location,p.url) image,\tt.sku,\tt.asin,\tg.name groupname,\tm.color,\tinfo.name,\tCONCAT(t.sku,'_',t.amazon_order_id) id,\tt.sales_channel market,\tt.currency,\tt.ship_service_level shipservice,\tt.ship_city city,\tt.shipping_price shipfee, \tt.ship_promotion_discount shipdiscount,\tau.id authid,\tord.remark remark,\tt.is_business_order isbusiness,\tt.item_promotion_discount itemdiscount, \tmt.marketplaceId,\tmt.name marketname,\tmt.region region,\tcase when q.submitfeedid is null and ord.feed_queueid is not null then '排队中' \telse case when ord.feed_queueid is null then '未上传' else '已上传' end end  feedstatus,\tord.feed_queueid feedid from t_orders_report t left join t_amazon_auth au on au.id=t.amazonAuthId left join t_amazon_group g on au.groupid=g.id left join t_marketplace mt on mt.point_name=t.sales_channel left join t_product_info info on info.sku=t.sku and t.amazonAuthId=info.amazonAuthId and t.asin=info.asin and info.marketplaceid=mt.marketplaceId left join t_picture p on p.id=info.image left join t_product_in_opt opt on opt.pid=info.id  left join  t_erp_material m on m.sku=ifnull(opt.msku,t.sku) and m.shopid=au.shop_id and m.isDelete = 0 left join  t_orders_remark ord on ord.amazon_order_id=t.amazon_order_id left join  t_amz_submitfeed_queue q on q.id=ord.feed_queueid where t.purchase_date between DATE_SUB(NOW(),INTERVAL 9 day) and DATE_SUB(NOW(),INTERVAL 3 day)  and t.order_status='Unshipped'", nativeQuery = true)
    public List<OrderItemAdaptorInfoDTO> find9To3DaysUnshippedOrders();

    public List<OrdersReport> findByAmazonAuthIdAndMarketplaceIdAndAmazonOrderId(BigInteger amazonAuthId, String marketplaceId, String amazonOrderId);

}