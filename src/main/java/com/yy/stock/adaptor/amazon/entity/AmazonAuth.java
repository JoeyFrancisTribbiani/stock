package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * @author minmin
 */
@Entity
@Table(name = "t_amazon_auth")
public class AmazonAuth {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Size(max = 30)
    @NotNull
    @Column(name = "sellerid", nullable = false, length = 30)
    private String sellerid;

    @Column(name = "groupid")
    private Long groupid;

    @Size(max = 10)
    @Column(name = "region", length = 10)
    private String region;

    @Size(max = 50)
    @Column(name = "MWSAuthToken", length = 50)
    private String mWSAuthToken;

    @Column(name = "disable")
    private Boolean disable;

    @Size(max = 10)
    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "pictureId")
    private Long pictureId;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "statusupdate")
    private Instant statusupdate;

    @Column(name = "productdate")
    private Instant productdate;

    @Column(name = "refreshinvtime")
    private Instant refreshinvtime;

    @Size(max = 500)
    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    @Column(name = "refresh_token_time")
    private Instant refreshTokenTime;

    @Size(max = 15)
    @Column(name = "aws_region", length = 15)
    private String awsRegion;

    @Column(name = "createtime")
    private Instant createtime;

    @Column(name = "opttime")
    private Instant opttime;

    @Size(max = 36)
    @Column(name = "oldid", length = 36)
    private String oldid;

    @Size(max = 50)
    @Column(name = "access_key_id", length = 50)
    private String accessKeyId;

    @Size(max = 50)
    @Column(name = "secret_key", length = 50)
    private String secretKey;

    @Size(max = 50)
    @Column(name = "role_arn", length = 50)
    private String roleArn;

    @Size(max = 50)
    @Column(name = "client_id", length = 50)
    private String clientId;

    @Size(max = 50)
    @Column(name = "client_secret", length = 50)
    private String clientSecret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMWSAuthToken() {
        return mWSAuthToken;
    }

    public void setMWSAuthToken(String mWSAuthToken) {
        this.mWSAuthToken = mWSAuthToken;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getStatusupdate() {
        return statusupdate;
    }

    public void setStatusupdate(Instant statusupdate) {
        this.statusupdate = statusupdate;
    }

    public Instant getProductdate() {
        return productdate;
    }

    public void setProductdate(Instant productdate) {
        this.productdate = productdate;
    }

    public Instant getRefreshinvtime() {
        return refreshinvtime;
    }

    public void setRefreshinvtime(Instant refreshinvtime) {
        this.refreshinvtime = refreshinvtime;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getRefreshTokenTime() {
        return refreshTokenTime;
    }

    public void setRefreshTokenTime(Instant refreshTokenTime) {
        this.refreshTokenTime = refreshTokenTime;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public Instant getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Instant createtime) {
        this.createtime = createtime;
    }

    public Instant getOpttime() {
        return opttime;
    }

    public void setOpttime(Instant opttime) {
        this.opttime = opttime;
    }

    public String getOldid() {
        return oldid;
    }

    public void setOldid(String oldid) {
        this.oldid = oldid;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRoleArn() {
        return roleArn;
    }

    public void setRoleArn(String roleArn) {
        this.roleArn = roleArn;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}