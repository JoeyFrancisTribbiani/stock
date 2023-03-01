package com.yy.stock.adaptor.amazon.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class AmazonAuthUpdateVO extends AmazonAuthVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
