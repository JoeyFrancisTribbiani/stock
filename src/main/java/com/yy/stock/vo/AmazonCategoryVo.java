package com.yy.stock.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.stock.entity.AmazonCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonCategoryVo extends AmazonCategory {
    private static final long serialVersionUID = 1L;
}
