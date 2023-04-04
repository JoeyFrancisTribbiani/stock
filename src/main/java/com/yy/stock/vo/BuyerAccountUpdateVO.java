package com.yy.stock.vo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BuyerAccountUpdateVO extends BuyerAccountVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
