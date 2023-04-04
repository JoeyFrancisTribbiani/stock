package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "t_shop")
@Accessors(chain = true)
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 公司名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 邀请码
     */
    @Column(name = "invitecode")
    private String invitecode;

    /**
     * 受邀请码
     */
    @Column(name = "fromcode")
    private String fromcode;

    @Column(name = "oldid")
    private String oldid;

    @Column(name = "boss_email")
    private String bossEmail;

}
