package com.yy.stock.vo;


import lombok.EqualsAndHashCode;

import java.io.Serializable;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class StatusUpdateVO extends StatusVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
