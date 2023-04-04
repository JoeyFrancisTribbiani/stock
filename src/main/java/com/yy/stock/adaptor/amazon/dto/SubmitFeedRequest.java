package com.yy.stock.adaptor.amazon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitFeedRequest {
    String content;
    String name;
    AmazonAuthority amazonAuthority;
    String feedType;
    UserInfo user;
    String feedoptions;


}
