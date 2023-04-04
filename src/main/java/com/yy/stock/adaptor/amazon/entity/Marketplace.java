package com.yy.stock.adaptor.amazon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "t_marketplace")
public class Marketplace {

    @Id
    @Column(name = "marketplaceId", nullable = false)
    @JsonProperty("marketplaceid")
    private String marketplaceId;

    @Column(name = "market")
    private String market;

    @Column(name = "name")
    private String name;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "region")
    private String region;

    @Column(name = "end_point")
    private String endPoint;

    @Column(name = "point_name")
    private String pointName;

    @Column(name = "accessKey")
    private String accessKey;

    @Column(name = "secretKey")
    private String secretKey;

    @Column(name = "dim_units")
    private String dimUnits;

    @Column(name = "weight_units")
    private String weightUnits;

    @Column(name = "currency")
    private String currency;

    @Column(name = "findex")
    private Integer findex;

    @Column(name = "adv_end_point")
    private String advEndPoint;

    @Column(name = "aws_access_key")
    private String awsAccessKey;

    @Column(name = "aws_secret_key")
    private String awsSecretKey;

    @Column(name = "associate_tag")
    private String associateTag;

    @Column(name = "developer_url")
    private String developerUrl;

    @Column(name = "dev_account_num")
    private String devAccountNum;

    @Column(name = "bytecode")
    private String bytecode;

    @Column(name = "sp_api_endpoint")
    private String spApiEndpoint;

    @Column(name = "aws_region")
    private String awsRegion;

}
