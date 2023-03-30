package com.yy.stock.adaptor.amazon.dto;

import lombok.Data;

@Data
public class SubmitFeedRequest {
    String content;
    String name;
    AmazonAuthority amazonAuthority;
    String feedType;
    UserInfo user;
    String feedoptions;


}
