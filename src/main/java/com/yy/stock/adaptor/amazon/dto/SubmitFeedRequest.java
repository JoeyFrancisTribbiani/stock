package com.yy.stock.adaptor.amazon.dto;

import lombok.Data;

import java.io.ByteArrayOutputStream;

@Data
public class SubmitFeedRequest {
    ByteArrayOutputStream content;
    String name;
    AmazonAuthority amazonAuthority;
    String feedType;
    UserInfo user;
    String feedoptions;
}
