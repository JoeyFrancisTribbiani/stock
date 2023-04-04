package com.yy.stock.adaptor.amazon.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 419430401949604446L;

    private Map<String, Object> userinfo;

    private String id;

    private String account;

    private Date createDate;

    private Date losingeffect;

    private Boolean logicDelete;

    private Boolean disable;

    private int member;

    private String passwordkey;

    private Date lastlogintime;

    private String lastloginip;

    private String lastloginsession;

    private String ftype;

    private Boolean isActive;

    private Boolean hasEmail;

    private String usertype;

    private String companyid;

    private String session;

    private String deptid;

    private List<String> roles;

    private List<String> groups;

    private List<String> datalimits;

    public Boolean isLimit(String dataType) {
        return datalimits.contains(dataType);
    }

    ;
//    public Boolean isLimit(UserLimitDataType dataType) {
//    	return datalimits.contains(dataType.getCode());
//    };
}
