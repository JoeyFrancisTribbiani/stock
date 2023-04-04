package com.yy.stock.adaptor.amazon.dto;

import com.yy.stock.adaptor.amazon.entity.Marketplace;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AmazonAuthority implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4872652002637528809L;
    String id;
    Marketplace marketPlace;
    String groupname;
    Date lastupdate;
    String useApi;
    private String shopId;
    private String sellerid;
    private String mwsauthtoken;
    private String name;
    private Boolean disable;
    private String region;
    private String pictureId;
    private String status;
    private Date statusupdate;
    private Date productdate;
    private Date opttime;
    private String groupid;
    private Date createtime;
    private Date refreshinvtime;
    private String refreshToken;
    private Date refreshTokenTime;
    private String AWSRegion;
    private String accessKeyId;
    private String secretKey;
    private String roleArn;
    private String clientId;
    private String clientSecret;
    private Date captureDateTime;
    private int requestOrderReportTime = 0;

    public Date getCaptureDateTime() {
        if (captureDateTime == null) {
            Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
            captureDateTime = c.getTime();
        }
        return captureDateTime;
    }

}