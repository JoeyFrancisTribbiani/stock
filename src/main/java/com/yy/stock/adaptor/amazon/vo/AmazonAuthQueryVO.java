package com.yy.stock.adaptor.amazon.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AmazonAuthQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;


    /**
     * 用户ID
     */
    private String shopId;


    /**
     * 卖家ID
     */
    private String sellerid;

    private String groupid;

    private String region;


    /**
     * 卖家授权码
     */
    private String MWSAuthToken;

    private Boolean disable;

    private String name;

    private String pictureId;

    private String status;

    private Date statusupdate;

    private Date productdate;

    private Date refreshinvtime;

    private String refreshToken;

    private Date refreshTokenTime;

    private String awsRegion;

    private Date createtime;

    private Date opttime;

    private String oldid;

    private String accessKeyId;

    private String secretKey;

    private String roleArn;

    private String clientId;

    private String clientSecret;

}
