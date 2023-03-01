package com.yy.stock.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class BuyerAccountUpdateVO extends BuyerAccountVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
