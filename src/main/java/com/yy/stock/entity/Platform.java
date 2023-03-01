package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * stock record from supplier
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "platform")
public class Platform implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "login_url", nullable = false)
    private String loginUrl;

    @Column(name = "msg_url")
    private String msgUrl;

}
