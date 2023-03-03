package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmzOrderItem;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmzOrderItemRepository extends JpaRepository<AmzOrderItem, String>, JpaSpecificationExecutor<AmzOrderItem> {

    @Query(value = "select  t.purchase_date  as buydate,    ma.order_status as  itemstatus,    ma.order_status as  orderstatus,    t.amazon_order_id as  orderid,    g.id as  groupid,    ma.fulfillment_channel as   channel, ifnull(t.item_price,t.QuantityOrdered*info.price) as  orderprice, ifnull(t.item_price/t.QuantityOrdered,info.price) as  itemprice, t.QuantityOrdered as  quantity, ifnull(p.location,p.url) as  image, t.sku, t.asin, g.name as  groupname, m.color, info.name, CONCAT(t.sku,'_',t.amazon_order_id)  as id, ma.sales_channel as  market, t.currency, ma.ship_service_level as  shipservice, addr.city as  city, t.shipping_price as  shipfee, t.ship_promotion_discount as  shipdiscount, au.id as  authid, ord.remark as  remark, ma.isBusinessOrder as  isbusiness, t.item_promotion_discount as  itemdiscount, mt.marketplaceId, mt.name as  marketname, mt.region as  region, case when q.submitfeedid is null and ord.feed_queueid is not null then '排队中'  else case when ord.feed_queueid is null then '未上传' else '已上传' end end as   feedstatus, ord.feed_queueid as  feedid  FROM t_amz_order_item t left join t_amz_order_main ma on ma.amazon_order_id=t.amazon_order_id and t.marketplaceid=ma.marketplaceid AND ma.amazonAuthId=t.amazonAuthId left join t_marketplace mt on mt.marketplaceId=ma.marketplaceId left join t_amz_order_buyer_ship_address addr on addr.id=ma.buyer_shipping_address_id left join t_amazon_auth au on au.id=t.amazonAuthId left join t_amazon_group g on au.groupid=g.id left join t_product_info info on info.sku=t.sku and t.amazonAuthId=info.amazonAuthId and info.marketplaceid=t.marketplaceId LEFT JOIN t_product_in_opt opt ON opt.pid=info.id left join t_picture p on p.id=info.image left join t_erp_material m on m.sku=ifnull(opt.msku,info.sku) and m.shopid=au.shop_id and m.isDelete = 0 left join t_orders_remark ord on ord.amazon_order_id=t.amazon_order_id left join t_amz_submitfeed_queue q on q.id=ord.feed_queueid where  t.purchase_date between DATE_SUB(NOW(),INTERVAL 3 day) and DATE_SUB(NOW(),INTERVAL 0 day) and ma.order_status='Unshipped'", nativeQuery = true)
    public List<OrderItemAdaptorInfoDTO> find3DaysUnshippedOrders();
}