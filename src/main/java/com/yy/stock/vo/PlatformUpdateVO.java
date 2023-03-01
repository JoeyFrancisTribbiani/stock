package com.yy.stock.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlatformUpdateVO extends PlatformVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
