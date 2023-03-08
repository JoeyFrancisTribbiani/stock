package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.OrdersReportDTO;
import com.yy.stock.adaptor.amazon.entity.AmzOrdersAddress;
import com.yy.stock.adaptor.amazon.entity.OrdersReport;
import com.yy.stock.adaptor.amazon.repository.OrdersReportRepository;
import com.yy.stock.adaptor.amazon.vo.OrdersReportQueryVO;
import com.yy.stock.adaptor.amazon.vo.OrdersReportUpdateVO;
import com.yy.stock.adaptor.amazon.vo.OrdersReportVO;
import com.yy.stock.common.util.GeneralUtil;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.entity.SyncOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class OrdersReportService {

    @Autowired
    private OrdersReportRepository ordersReportRepository;
    @Autowired
    private AmzOrdersAddressService amzOrdersAddressService;

    public String save(OrdersReportVO vO) {
        OrdersReport bean = new OrdersReport();
        BeanUtils.copyProperties(vO, bean);
        bean = ordersReportRepository.save(bean);
        return bean.getId();
    }

    public void treatReportTxt(SyncOrder provider, BufferedReader br) {
        Map<String, Integer> titleList = new HashMap<String, Integer>();
        int lineNumber = 0;
        String line;
        try {
            try {
                while ((line = br.readLine()) != null) {
                    String[] info = line.split("\t");
                    int length = info.length;
                    if (lineNumber == 0) {
                        for (int i = 0; i < length; i++) {
                            titleList.put(info[i], i);
                        }
                    } else {
                        String amazon_order_id = GeneralUtil.getStrValue(info, titleList, "order-id");
                        var records = ordersReportRepository.findByAmazonAuthIdAndMarketplaceIdAndAmazonOrderId(provider.getAmazonAuthId(), provider.getMarketplaceId(), amazon_order_id);
                        if (records == null || records.size() == 0) {
//                            continue;
                            var record = new OrdersReport();
                            record.setAmazonAuthId(provider.getAmazonAuthId());
                            record.setAmazonOrderId(amazon_order_id);
                            record.setMarketplaceId(provider.getMarketplaceId());
                            var address = amzOrdersAddressService.getByOrderReport(record);
                            if (address == null) {
                                continue;
                            } else {

                                saveAddressFromReportLine(titleList, info, amazon_order_id, record, address);
                            }
                        } else {
                            var record = records.get(0);
                            var address = amzOrdersAddressService.getByOrderReport(record);
                            if (address == null) {
                                address = new AmzOrdersAddress();
                                address.setMarketplaceId(record.getMarketplaceId());
                                address.setAmazonAuthId(record.getAmazonAuthId());
                                address.setAmazonOrderId(record.getAmazonOrderId());

                            } else if (address.getName() != null && !address.getName().equals("")) {
                                continue;
                            }
                            saveAddressFromReportLine(titleList, info, amazon_order_id, record, address);
                        }
                    }
                    lineNumber++;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.debug(e.getMessage());
            }
        } finally {
            titleList.clear();
            titleList = null;
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            System.gc();
        }

    }

    private void saveAddressFromReportLine(Map<String, Integer> titleList, String[] info, String amazon_order_id, OrdersReport record, AmzOrdersAddress address) {
        String name = GeneralUtil.getStrValue(info, titleList, "recipient-name");
        String addressLine1 = GeneralUtil.getStrValue(info, titleList, "ship-address-1");
        String addressLine2 = GeneralUtil.getStrValue(info, titleList, "ship-address-2");
        String addressLine3 = GeneralUtil.getStrValue(info, titleList, "ship-address-3");
//                        String shipCity = GeneralUtil.getStrValue(info, titleList, "ship-city");
        String postalCode = GeneralUtil.getStrValue(info, titleList, "ship-postal-code");
        String phone = GeneralUtil.getStrValue(info, titleList, "ship-phone-number");
        String instructions = GeneralUtil.getStrValue(info, titleList, "delivery-Instructions");
        String cpf = GeneralUtil.getStrValue(info, titleList, "cpf");
        address.setName(name);
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setAddressLine3(addressLine3);
        address.setPhone(phone);
        address.setPostalCode(postalCode);
        address.setAddressType(instructions);
        address.setDistrict(cpf);
        try {
            amzOrdersAddressService.save(address);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("SKU:" + record.getSku() + "的订单（" + amazon_order_id + "）同步地址报错!" + e.getMessage());
        }
    }

    public List<OrderItemAdaptorInfoDTO> get9To3DaysUnshippedOrders() {
        return ordersReportRepository
                .find9To3DaysUnshippedOrders();
    }


    public void delete(String id) {
        ordersReportRepository.deleteById(id);
    }

    public void update(String id, OrdersReportUpdateVO vO) {
        OrdersReport bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        ordersReportRepository.save(bean);
    }

    public OrdersReportDTO getById(String id) {
        OrdersReport original = requireOne(id);
        return toDTO(original);
    }

    public Page<OrdersReportDTO> query(OrdersReportQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private OrdersReportDTO toDTO(OrdersReport original) {
        OrdersReportDTO bean = new OrdersReportDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private OrdersReport requireOne(String id) {
        return ordersReportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
