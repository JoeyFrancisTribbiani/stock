package com.yy.stock.adaptor.amazon.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Marketplace implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5558303729383204310L;

    private String marketplaceid;

    private String market;

    private String name;

    private String regionName;

    private String region;

    private String endPoint;

    private String pointName;

    private String accesskey;

    private String secretkey;

    private String dimUnits;

    private String weightUnits;

    private String currency;

    private Integer findex;

    private String advEndPoint;

    private String AwsAccessKey;

    private String AwsSecretKey;

    private String AssociateTag;

    private String developerUrl;

    private String devAccountNum;

    private String bytecode;

    private String spApiEndpoint;

    private String awsRegion;


}