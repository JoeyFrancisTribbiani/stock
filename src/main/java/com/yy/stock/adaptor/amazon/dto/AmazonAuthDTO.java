package com.yy.stock.adaptor.amazon.dto;

import com.yy.stock.adaptor.amazon.entity.AmazonAuth;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link AmazonAuth} entity
 */
@Data
public class AmazonAuthDTO implements Serializable {
    private Long id;
    private Long shopId;
    @Size(max = 30)
    @NotNull
    private String sellerid;
    private Long groupid;
    @Size(max = 10)
    private String region;
    @Size(max = 50)
    private String mWSAuthToken;
    private Boolean disable;
    @Size(max = 10)
    private String name;
    private Long pictureId;
    @Size(max = 20)
    private String status;
    private Instant statusupdate;
    private Instant productdate;
    private Instant refreshinvtime;
    @Size(max = 500)
    private String refreshToken;
    private Instant refreshTokenTime;
    @Size(max = 15)
    private String awsRegion;
    private Instant createtime;
    private Instant opttime;
    @Size(max = 36)
    private String oldid;
    @Size(max = 50)
    private String accessKeyId;
    @Size(max = 50)
    private String secretKey;
    @Size(max = 50)
    private String roleArn;
    @Size(max = 50)
    private String clientId;
    @Size(max = 50)
    private String clientSecret;
}